/**
 * 
 */
package py.com.mensajeria.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import py.com.mensajeria.model.ejb.DestinatarioManager;
import py.com.mensajeria.model.ejb.RolManager;
import py.com.mensajeria.model.entity.Destinatario;
import py.com.mensajeria.model.entity.Empresa;
import py.com.mensajeria.services.UserDetailsMensajeria;
import py.com.mensajeria.utils.Constantes;
import py.com.mensajeria.utils.ExceptionMensajeria;
import py.com.mensajeria.utils.utils.FiltroDTO;
import py.com.mensajeria.utils.utils.ReglaDTO;

import com.google.gson.Gson;

/**
 * @author Miguel
 *
 */
@Controller
@RequestMapping(value = "/destinatarios")
public class DestinatarioController extends BaseController {
	
	private String atributosDestinatario = "id,nombre,destinatario,activo";

	@EJB(mappedName = "java:global/mensajeriaapp-ear/mensajeriaapp-ejb/DestinatarioManagerImpl")
	private DestinatarioManager destinatarioManager;
	
	/**
	 * Servicio REST para listar todos los destinatarios
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	HashMap<String, Object> lista(@ModelAttribute("_search") boolean filtrar,
			@ModelAttribute("filters") String filtros,
			@ModelAttribute("page") Integer pagina,
			@ModelAttribute("rows") Integer cantidad,
			@ModelAttribute("sidx") String ordenarPor,
			@ModelAttribute("sord") String sentidoOrdenamiento,
			@ModelAttribute("todos") boolean todos,
			@ModelAttribute("idEmpresa") String idEmpresa) {

		// map para el retorno que contendra la lista y los metadatos
		HashMap<String, Object> retorno = new HashMap<String, Object>();

		// map para los metadatos del retorno
		HashMap<String, Object> meta = new HashMap<String, Object>();

		UserDetailsMensajeria userDetails = UserDetailsMensajeria
				.getUsuarioAutenticado();

		Destinatario ejemplo = new Destinatario();
		try {

			
			Gson gson = new Gson();
			String camposFiltros = null;
			String valorFiltro = null;

			if (filtrar) {
				FiltroDTO filtro = gson.fromJson(filtros, FiltroDTO.class);
				if (filtro.getGroupOp().compareToIgnoreCase(Constantes.OP_OR) == 0) {
					for (ReglaDTO regla : filtro.getRules()) {
						if (camposFiltros == null) {
							camposFiltros = regla.getField();
							valorFiltro = regla.getData();
						} else {
							camposFiltros += "," + regla.getField();
						}
					}
				} else {
					ejemplo = generarEjemplo(filtro, ejemplo);
				}

			}
			
			if(idEmpresa != null & idEmpresa.toString().compareToIgnoreCase("") != 0){
				ejemplo.setEmpresa(new Empresa(Long.valueOf(idEmpresa)));
			}
			
			pagina = pagina != null ? pagina : 1;
			Integer total = 0;

			if (!todos) {
				total = destinatarioManager.total(ejemplo, true, true);
			}

			Integer inicio = ((pagina - 1) < 0 ? 0 : pagina - 1) * cantidad;

			if (total < inicio) {
				inicio = total - total % cantidad;
				pagina = total / cantidad;
			}

			List<Map<String, Object>> listMapDestinatarios = destinatarioManager
					.listAtributos(ejemplo, atributosDestinatario.split(","), todos,
							inicio, cantidad, ordenarPor.split(","),
							sentidoOrdenamiento.split(","), true, true, camposFiltros, valorFiltro);
				
			if (todos) {
				total = listMapDestinatarios.size();
			}

			retorno.put("destinatarios", listMapDestinatarios);

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"Se listaron exitosamentes los destinatarios", "300",
					ESTADO_EXITO, OP_VISUALIZACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), null, false);

			meta.put("totalDatos", total);
			meta.put("pagina", pagina);

		} catch (Exception ex) {

			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al obtener la lista de destinatarios", "300",
					ESTADO_ERROR, OP_VISUALIZACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), ex, false);
		}

		retorno.put("meta", meta);
		return retorno;

	}
	
	/**
	 * Servicio REST para persistir un nuevo destinatario
	 * 
	 * @param nuevo
	 *            entidad a persistir
	 */

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HashMap<String, Object> agregarDestinatario(@RequestBody Destinatario nuevo,  HttpServletResponse response) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		HashMap<String, Object> meta = new HashMap<String, Object>();
		UserDetailsMensajeria userDetails = UserDetailsMensajeria
				.getUsuarioAutenticado();

		logger.info("Inició correctamente el método agregar destinatario por el usuario "
				+ userDetails.getNombreCompleto());

		try {
			// Controlamos que el nombre del destinatario no sea nulo o vacío.
			if (nuevo.getNombre() == null
					|| nuevo.getNombre().compareToIgnoreCase("") == 0) {
				throw new ExceptionMensajeria("nombre","243",
						"El nombre del destinatario no puede estar vacío");
			}

			// Controlamos que el nombre del rol no está ya en uso.
			Destinatario ejemploNombre = new Destinatario();
			ejemploNombre.setNombre(nuevo.getNombre());
			ejemploNombre.setEmpresa(new Empresa(userDetails.getIdEmpresa()));
			if (destinatarioManager.total(ejemploNombre) > 0) {
				throw new ExceptionMensajeria("nombre","244", "El nombre "
						+ nuevo.getNombre() + " ya está en uso");
			}



			destinatarioManager.crear(nuevo, userDetails.getIdUsuario());

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"El destinatario se agregó con éxito", "300", ESTADO_EXITO,
					OP_ALTA, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), null, false);

			retorno.put("destinatario", nuevo);
			response.setStatus(201);

		} catch (Exception ex) {

			response.setStatus(422);
			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al agregar el destinatario", "300", ESTADO_ERROR,
					OP_ALTA, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), ex, false);

		}
		retorno.put("meta", meta);
		return retorno;
	}
	
	/**
	 * Servicio para editar un destinatario
	 * 
	 * @param empresa
	 *            la entidad Destinatario a modificar recibida de la vista
	 */

	@RequestMapping(value="/{id}", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public HashMap<String, Object> modificarDestinatario(@PathVariable("id") Long id,
			@RequestBody Destinatario destinatario) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		HashMap<String, Object> meta = new HashMap<String, Object>();
		UserDetailsMensajeria userDetails = UserDetailsMensajeria
				.getUsuarioAutenticado();

		try {

			Destinatario anterior = destinatarioManager.get(id);

			destinatario.setId(id);

			// Controlamos que el nombre del destinatario no sea nulo o vacío.
			if (destinatario.getNombre() == null
					|| destinatario.getNombre().compareToIgnoreCase("") == 0) {
				throw new ExceptionMensajeria("nombre","243",
						"El nombre del destinatario no puede estar vacío");
			}

			Destinatario ejemploNombre = new Destinatario();
			ejemploNombre.setNombre(destinatario.getNombre());
			ejemploNombre.setEmpresa(new Empresa(userDetails.getIdEmpresa()));
			Map<String, Object> destinatarioBD = destinatarioManager.getAtributos(
					ejemploNombre, atributosDestinatario.split(","), false, true);

			if (destinatarioBD != null
					&& destinatarioBD.containsKey("id")
					&& !(Long.parseLong(destinatarioBD.get("id").toString()) == anterior
							.getId())) {
				throw new ExceptionMensajeria("nombre","300", "El destinatario con el nombre "
						+ destinatario.getNombre() + " ya existe");
			}


			destinatario = destinatarioManager.actualizar(destinatario, userDetails.getIdUsuario());

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"El destinatario se modifico con éxito", "300", ESTADO_EXITO,
					OP_ALTA, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), null, false);

			retorno.put("destinatario", destinatario);

		} catch (Exception ex) {

			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al agregar el rol", "300", ESTADO_ERROR,
					OP_ALTA, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), ex, false);
		}
		retorno.put("meta", meta);
		return retorno;
	}
	
	/**
	 * Recibe un string que es un json y devuelve una entidad con los valores
	 * recibidos
	 * 
	 * @param filtros
	 * @return
	 * @throws Exception
	 */
	public static Destinatario generarEjemplo(FiltroDTO filtro, Destinatario ejemplo)
			throws Exception {

		if (ejemplo == null) {
			ejemplo = new Destinatario();
		}

		for (ReglaDTO regla : filtro.getRules()) {

			if ("id".compareToIgnoreCase(regla.getField()) == 0
					&& regla.getData() != null && !regla.getData().isEmpty()) {
				ejemplo.setId(Long.parseLong(regla.getData()));

			} else if ("nombre".compareToIgnoreCase(regla.getField()) == 0
					&& regla.getData() != null && !regla.getData().isEmpty()) {
				ejemplo.setNombre(regla.getData());

			}
		}
		return ejemplo;
	}

}


