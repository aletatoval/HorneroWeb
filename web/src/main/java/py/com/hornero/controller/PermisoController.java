package py.com.hornero.controller;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

import py.com.hornero.model.ejb.PermisoManager;
import py.com.hornero.model.entity.Permiso;
import py.com.hornero.services.UserDetailsHornero;
import py.com.hornero.utils.Constantes;
import py.com.hornero.utils.ExceptionHornero;
import py.com.hornero.utils.utils.FiltroDTO;
import py.com.hornero.utils.utils.ReglaDTO;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/permisos")
public class PermisoController extends BaseController {

	private String atributosPermisos = "id,nombre,activo";

	@EJB(mappedName = "java:global/horneroapp-ear/horneroapp-ejb/PermisoManagerImpl")
	private PermisoManager permisoManager;

	/**
	 * Servicio REST para listar todos los permisos
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
			@ModelAttribute("todos") boolean todos) {

		// map para el retorno que contendra la lista y los metadatos
		HashMap<String, Object> retorno = new HashMap<String, Object>();

		// map para los metadatos del retorno
		HashMap<String, Object> meta = new HashMap<String, Object>();
			
		UserDetailsHornero userDetails = UserDetailsHornero
				.getUsuarioAutenticado();

		Permiso ejemplo = new Permiso();
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
			
			//ejemplo.setActivo("S");

			pagina = pagina != null ? pagina : 1;
			Integer total = 0;

			if (!todos) {
				total = permisoManager.total(ejemplo, true, true);
			}

			Integer inicio = ((pagina - 1) < 0 ? 0 : pagina - 1) * cantidad;

			if (total < inicio) {
				inicio = total - total % cantidad;
				pagina = total / cantidad;
			}

			List<Map<String, Object>> listMapPermisos = permisoManager
					.listAtributos(ejemplo, atributosPermisos.split(","), todos,
							inicio, cantidad, ordenarPor.split(","),
							sentidoOrdenamiento.split(","), true, true,
							camposFiltros, valorFiltro);
			if (todos) {
				total = listMapPermisos.size();
			}

			retorno.put("permisos", listMapPermisos);

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"Se listaron exitosamente los permisos", "300",
					ESTADO_EXITO, OP_VISUALIZACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), null, false);

			meta.put("totalDatos", total);
			meta.put("pagina", pagina);

		} catch (Exception ex) {

			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al obtener la lista de permisos", "300",
					ESTADO_ERROR, OP_VISUALIZACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), ex, false);
		}

		retorno.put("meta", meta);
		return retorno;

	}

	/**
	 * Servicio REST para obtener un permiso por id
	 * 
	 * @param id
	 *            el id del permiso a obtener
	 */

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	HashMap<String, Object> obtener(@PathVariable("id") Long id) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		HashMap<String, Object> meta = new HashMap<String, Object>();
		UserDetailsHornero userDetails = UserDetailsHornero
				.getUsuarioAutenticado();

		try {

			Map<String, Object> permiso = permisoManager.getAtributos(
					new Permiso(id), atributosPermisos.split(","));

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"El usuario se obtuvo exitosamente", "300", ESTADO_EXITO,
					OP_VISUALIZACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), null, false);

			retorno.put("permiso", permiso);

		} catch (Exception ex) {

			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al obtener el permiso solicitado", "300",
					ESTADO_ERROR, OP_VISUALIZACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), ex, false);

		}

		retorno.put("meta", meta);
		return retorno;

	}

	/**
	 * Servicio REST para persistir un nuevo permiso
	 * 
	 * @param nuevo
	 *            entidad a persistir
	 */

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HashMap<String, Object> agregarPermiso(@RequestBody Permiso nuevo,  HttpServletResponse response) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		HashMap<String, Object> meta = new HashMap<String, Object>();
		UserDetailsHornero userDetails = UserDetailsHornero
				.getUsuarioAutenticado();

		logger.info("Inició correctamente el método agregar permisos por el usuario "
				+ userDetails.getNombreCompleto());

		try {
			// Controlamos que el nombre de la empresa no sea nulo o vacío.
			if (nuevo.getNombre() == null
					|| nuevo.getNombre().compareToIgnoreCase("") == 0) {
				throw new ExceptionHornero("nombre","243",
						"El nombre del permiso no puede estar vacío");
			}

			// Controlamos que el nombre de la empresa no está ya en uso.
			Permiso ejemploNombre = new Permiso();
			ejemploNombre.setNombre(nuevo.getNombre());

			if (permisoManager.total(ejemploNombre) > 0) {
				throw new ExceptionHornero("nombre","244", "El nombre "
						+ nuevo.getNombre() + " ya está en uso");
			}

			permisoManager.crear(nuevo, userDetails.getIdUsuario());

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"El permiso se agregó con éxito", "300", ESTADO_EXITO,
					OP_ALTA, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), null, false);

			retorno.put("permiso", nuevo);
			response.setStatus(201);

		} catch (Exception ex) {

			response.setStatus(422);
			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al agregar el permiso", "300", ESTADO_ERROR,
					OP_ALTA, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), ex, false);

		}
		retorno.put("meta", meta);
		return retorno;
	}

	/**
	 * Servicio para editar un permiso
	 * 
	 * @param empresa
	 *            la entidad Empresa a modificar recibida de la vista
	 */

	@RequestMapping(value="/{id}", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public HashMap<String, Object> modificarPermiso(@PathVariable("id") Long id,
			@RequestBody Permiso permiso) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		HashMap<String, Object> meta = new HashMap<String, Object>();
		UserDetailsHornero userDetails = UserDetailsHornero
				.getUsuarioAutenticado();

		try {

			Permiso anterior = permisoManager.get(id);

			permiso.setId(id);

			// Controlamos que el nombre de la empresa no sea nulo o vacío.
			if (permiso.getNombre() == null
					|| permiso.getNombre().compareToIgnoreCase("") == 0) {
				throw new ExceptionHornero("nombre","243",
						"El nombre del permiso no puede estar vacío");
			}

			Permiso ejemploNombre = new Permiso();
			ejemploNombre.setNombre(permiso.getNombre());

			Map<String, Object> permisoBD = permisoManager.getAtributos(
					ejemploNombre, atributosPermisos.split(","), false, true);

			if (permisoBD != null
					&& permisoBD.containsKey("id")
					&& !(Long.parseLong(permisoBD.get("id").toString()) == anterior
							.getId())) {
				throw new ExceptionHornero("nombre","300", "El permiso con el nombre "
						+ permiso.getNombre() + " ya existe");
			}
			

			permisoManager.actualizar(permiso, userDetails.getIdUsuario());

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"El permiso se modifico con éxito", "300", ESTADO_EXITO,
					OP_ALTA, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), null, false);

			retorno.put("permiso", permiso);

		} catch (Exception ex) {

			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al agregar el permiso", "300", ESTADO_ERROR,
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
	public static Permiso generarEjemplo(FiltroDTO filtro, Permiso ejemplo)
			throws Exception {

		if (ejemplo == null) {
			ejemplo = new Permiso();
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
