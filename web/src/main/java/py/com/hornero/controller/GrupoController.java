/**
 * 
 */
package py.com.hornero.controller;

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

import py.com.hornero.model.ejb.DestinatarioManager;
import py.com.hornero.model.ejb.GrupoManager;
import py.com.hornero.model.entity.Empresa;
import py.com.hornero.model.entity.Grupo;
import py.com.hornero.services.UserDetailsHornero;
import py.com.hornero.utils.Constantes;
import py.com.hornero.utils.ExceptionHornero;
import py.com.hornero.utils.utils.FiltroDTO;
import py.com.hornero.utils.utils.ReglaDTO;

import com.google.gson.Gson;

/**
 * @author Miguel
 *
 */
@Controller
@RequestMapping(value = "/grupos")
public class GrupoController extends BaseController{
	
	private String atributosGrupo = "id,nombre,activo";

	@EJB(mappedName = "java:global/mensajeriaapp-ear/mensajeriaapp-ejb/GrupoManagerImpl")
	private GrupoManager grupoManager;
	
	/**
	 * Servicio REST para listar todos los grupos
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

		UserDetailsHornero userDetails = UserDetailsHornero
				.getUsuarioAutenticado();

		Grupo ejemplo = new Grupo();
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
				total = grupoManager.total(ejemplo, true, true);
			}

			Integer inicio = ((pagina - 1) < 0 ? 0 : pagina - 1) * cantidad;

			if (total < inicio) {
				inicio = total - total % cantidad;
				pagina = total / cantidad;
			}

			List<Map<String, Object>> listMapGrupos = grupoManager
					.listAtributos(ejemplo, atributosGrupo.split(","), todos,
							inicio, cantidad, ordenarPor.split(","),
							sentidoOrdenamiento.split(","), true, true, camposFiltros, valorFiltro);
				
			if (todos) {
				total = listMapGrupos.size();
			}

			retorno.put("grupos", listMapGrupos);

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"Se listaron exitosamentes los grupos", "300",
					ESTADO_EXITO, OP_VISUALIZACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), null, false);

			meta.put("totalDatos", total);
			meta.put("pagina", pagina);

		} catch (Exception ex) {

			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al obtener la lista de grupos", "300",
					ESTADO_ERROR, OP_VISUALIZACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), ex, false);
		}

		retorno.put("meta", meta);
		return retorno;

	}
	
	/**
	 * Servicio REST para persistir un nuevo grupo
	 * 
	 * @param nuevo
	 *            entidad a persistir
	 */

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HashMap<String, Object> agregarGrupo(@RequestBody Grupo nuevo,  HttpServletResponse response) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		HashMap<String, Object> meta = new HashMap<String, Object>();
		UserDetailsHornero userDetails = UserDetailsHornero
				.getUsuarioAutenticado();

		logger.info("Inició correctamente el método agregar grupo por el usuario "
				+ userDetails.getNombreCompleto());

		try {
			// Controlamos que el nombre del grupo no sea nulo o vacío.
			if (nuevo.getNombre() == null
					|| nuevo.getNombre().compareToIgnoreCase("") == 0) {
				throw new ExceptionHornero("nombre","243",
						"El nombre del grupo no puede estar vacío");
			}

			// Controlamos que el nombre del grupo no está ya en uso.
			Grupo ejemploNombre = new Grupo();
			ejemploNombre.setNombre(nuevo.getNombre());
			ejemploNombre.setEmpresa(new Empresa(userDetails.getIdEmpresa()));
			
			if (grupoManager.total(ejemploNombre) > 0) {
				throw new ExceptionHornero("nombre","244", "El nombre "
						+ nuevo.getNombre() + " ya está en uso");
			}


			nuevo.setEmpresa(new Empresa(userDetails.getIdEmpresa()));
			
			nuevo = grupoManager.crear(nuevo, userDetails.getIdUsuario());

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"El grupo se agregó con éxito", "300", ESTADO_EXITO,
					OP_ALTA, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), null, false);

			retorno.put("grupo", nuevo);
			response.setStatus(201);

		} catch (Exception ex) {

			response.setStatus(422);
			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al agregar el grupo", "300", ESTADO_ERROR,
					OP_ALTA, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), ex, false);

		}
		retorno.put("meta", meta);
		return retorno;
	}
	
	/**
	 * Servicio para editar un grupo
	 * 
	 * @param empresa
	 *            la entidad Grupo a modificar recibida de la vista
	 */

	@RequestMapping(value="/{id}", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public HashMap<String, Object> modificarGrupo(@PathVariable("id") Long id,
			@RequestBody Grupo grupo) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		HashMap<String, Object> meta = new HashMap<String, Object>();
		UserDetailsHornero userDetails = UserDetailsHornero
				.getUsuarioAutenticado();

		try {

			Grupo anterior = grupoManager.get(id);

			grupo.setId(id);

			// Controlamos que el nombre del grupo no sea nulo o vacío.
			if (grupo.getNombre() == null
					|| grupo.getNombre().compareToIgnoreCase("") == 0) {
				throw new ExceptionHornero("nombre","243",
						"El nombre del grupo no puede estar vacío");
			}

			Grupo ejemploNombre = new Grupo();
			ejemploNombre.setNombre(grupo.getNombre());
			ejemploNombre.setEmpresa(new Empresa(userDetails.getIdEmpresa()));
			Map<String, Object> destinatarioBD = grupoManager.getAtributos(
					ejemploNombre, atributosGrupo.split(","), false, true);

			if (destinatarioBD != null
					&& destinatarioBD.containsKey("id")
					&& !(Long.parseLong(destinatarioBD.get("id").toString()) == anterior
							.getId())) {
				throw new ExceptionHornero("nombre","300", "El grupo con el nombre "
						+ grupo.getNombre() + " ya existe");
			}


			grupo = grupoManager.actualizar(grupo, userDetails.getIdUsuario());

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"El destinatario se modifico con éxito", "300", ESTADO_EXITO,
					OP_ALTA, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), null, false);

			retorno.put("grupo", grupo);

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
	public static Grupo generarEjemplo(FiltroDTO filtro, Grupo ejemplo)
			throws Exception {

		if (ejemplo == null) {
			ejemplo = new Grupo();
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
