/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.hornero.controller;

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

import com.google.gson.Gson;

import py.com.hornero.model.ejb.RolManager;
import py.com.hornero.model.ejb.RolPermisoManager;
import py.com.hornero.model.entity.Empresa;
import py.com.hornero.model.entity.Rol;
import py.com.hornero.model.entity.RolPermiso;
import py.com.hornero.services.UserDetailsHornero;
import py.com.hornero.utils.Constantes;
import py.com.hornero.utils.ExceptionHornero;
import py.com.hornero.utils.json.JSONArray;
import py.com.hornero.utils.utils.FiltroDTO;
import py.com.hornero.utils.utils.ReglaDTO;

@Controller
@RequestMapping(value = "/roles")
public class RolController extends BaseController {

	private String atributosRol = "id,nombre,empresa.id,activo";

	@EJB(mappedName = "java:global/horneroapp-ear/horneroapp-ejb/RolManagerImpl")
	private RolManager rolManager;
	
	@EJB(mappedName = "java:global/horneroapp-ear/horneroapp-ejb/RolPermisoManagerImpl")
	private RolPermisoManager rolPermisoManager;

	/**
	 * Servicio REST para listar todos los roles
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
				.getFuncionarioAutenticado();

		Rol ejemplo = new Rol();
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
				total = rolManager.total(ejemplo, true, true);
			}

			Integer inicio = ((pagina - 1) < 0 ? 0 : pagina - 1) * cantidad;

			if (total < inicio) {
				inicio = total - total % cantidad;
				pagina = total / cantidad;
			}

			List<Map<String, Object>> listMapRoles = rolManager
					.listAtributos(ejemplo, atributosRol.split(","), todos,
							inicio, cantidad, ordenarPor.split(","),
							sentidoOrdenamiento.split(","), true, true, camposFiltros, valorFiltro);
			
			if(idEmpresa != null & idEmpresa.toString().compareToIgnoreCase("") != 0){
				for(Map<String, Object> rpm: listMapRoles){
					//String[] modulos = null;
					ArrayList<String> permisos = new ArrayList<String>();
					if(rpm != null && rpm.toString().compareToIgnoreCase("") != 0){
						Rol ejRol = new Rol();
						ejRol.setId(Long.valueOf(rpm.get("id").toString()));
						
						RolPermiso ejRolPermiso = new RolPermiso();
						ejRolPermiso.setEmpresa(new Empresa(Long.valueOf(idEmpresa)));	
						ejRolPermiso.setRol(ejRol);
						ejRolPermiso.setActivo("S");
						
						List<Map<String, Object>> listMapRolPermisos = rolPermisoManager.listAtributos(ejRolPermiso, "id,rol.id,permiso.id".split(","));
						int pos = 0;
						for(Map<String, Object> mod : listMapRolPermisos){
							if(mod != null && mod.toString().compareToIgnoreCase("") != 0){
								if(rpm.get("id").toString().compareToIgnoreCase(mod.get("rol.id").toString()) == 0){
									permisos.add(mod.get("permiso.id").toString());
								}
							}
						}
					}
					rpm.put("permisos", permisos);
				}
			}
			
			
			if (todos) {
				total = listMapRoles.size();
			}

			retorno.put("roles", listMapRoles);

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"Se listaron exitosamentes los roles", "300",
					ESTADO_EXITO, OP_VISUALIZACION, userDetails.getIdFuncionario(),
					 null, false);

			meta.put("totalDatos", total);
			meta.put("pagina", pagina);

		} catch (Exception ex) {

			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al obtener la lista de roles", "300",
					ESTADO_ERROR, OP_VISUALIZACION, userDetails.getIdFuncionario(),
					 ex, false);
		}

		retorno.put("meta", meta);
		return retorno;

	}
		
	
	/**
	 * Servicio para asignar permisos a un rol
	 * 
	 * @param empresa
	 *            
	 */

	@RequestMapping(value="/{id}/permisos", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public HashMap<String, Object> asignarModulosEntidad(@PathVariable("id") Long idEmpresa,
			@RequestBody String entidad, HttpServletResponse response) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		HashMap<String, Object> meta = new HashMap<String, Object>();
		UserDetailsHornero userDetails = UserDetailsHornero
				.getFuncionarioAutenticado();
		Gson gson = new Gson();
		try {
						
			RolPermiso empresaEntidad = gson.fromJson(entidad, RolPermiso.class);
			
			if(idEmpresa != null & idEmpresa.toString().compareToIgnoreCase(" ") != 0
					& empresaEntidad.getRol().getId() != null ){
				if(empresaEntidad.getPermisos() != null & empresaEntidad.getPermisos().size() != 0){
					
					rolPermisoManager.asociarRolPermiso(idEmpresa,empresaEntidad.getRol().getId(),
							empresaEntidad.getPermisos(), userDetails.getIdFuncionario());
				}else{
					
					Rol ejRol = new Rol();
					ejRol.setId(empresaEntidad.getRol().getId());
					
					RolPermiso ejRolPermiso = new RolPermiso();
					ejRolPermiso.setRol(ejRol);
					ejRolPermiso.setEmpresa(new Empresa(idEmpresa));
					ejRolPermiso.setActivo("S");
					
					List<Map<String, Object>> misPermisos = rolPermisoManager.listAtributos(ejRolPermiso, "id,permiso.id,permiso.nombre".split(","));
					
					for(Map<String, Object> rpm : misPermisos){
						
						rolPermisoManager.desactivar(Long.valueOf(rpm.get("id").toString()), userDetails.getIdFuncionario());
						
					}
				}
			}

			

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"Los permisos se asignaron con éxito", "300", ESTADO_EXITO,
					OP_ASOCIACION, userDetails.getIdFuncionario(),
					 null, false);

			response.setStatus(201);
			
		} catch (Exception ex) {
			response.setStatus(422);
			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al asignar los permisos  al rol", "300", ESTADO_ERROR,
					OP_ASOCIACION, userDetails.getIdFuncionario(),
					 ex, false);
		}
		retorno.put("errors", meta);
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
	public static Rol generarEjemplo(FiltroDTO filtro, Rol ejemplo)
			throws Exception {

		if (ejemplo == null) {
			ejemplo = new Rol();
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


@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
public @ResponseBody
HashMap<String, Object> obtener(@PathVariable("id") Long id) {

	HashMap<String, Object> retorno = new HashMap<String, Object>();
	HashMap<String, Object> meta = new HashMap<String, Object>();
	UserDetailsHornero userDetails = UserDetailsHornero
			.getFuncionarioAutenticado();

	try {

		Map<String, Object> rol = rolManager.getAtributos(
				new Rol(id), atributosRol.split(","));

		meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
				"El rol se obtuvo exitosamente", "300", ESTADO_EXITO,
				OP_VISUALIZACION, userDetails.getIdFuncionario(),
				 null, false);

		retorno.put("rol", rol);

	} catch (Exception ex) {

		meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
				"Error al obtener el rol solicitado", "300",
				ESTADO_ERROR, OP_VISUALIZACION, userDetails.getIdFuncionario(),
				 ex, false);

	}

	retorno.put("meta", meta);
	return retorno;

}

/**
 * Servicio REST para persistir un nuevo rol
 * 
 * @param nuevo
 *            entidad a persistir
 */

@RequestMapping(method = RequestMethod.POST, produces = "application/json")
@ResponseBody
public HashMap<String, Object> agregarRol(@RequestBody Rol nuevo,  HttpServletResponse response) {

	HashMap<String, Object> retorno = new HashMap<String, Object>();
	HashMap<String, Object> meta = new HashMap<String, Object>();
	UserDetailsHornero userDetails = UserDetailsHornero
			.getFuncionarioAutenticado();

	logger.info("Inició correctamente el método agregar rol por el usuario "
			+ userDetails.getNombreCompleto());

	try {
		// Controlamos que el nombre del rol no sea nulo o vacío.
		if (nuevo.getNombre() == null
				|| nuevo.getNombre().compareToIgnoreCase("") == 0) {
			throw new ExceptionHornero("nombre","243",
					"El nombre del rol no puede estar vacío");
		}

		// Controlamos que el nombre del rol no está ya en uso.
		Rol ejemploNombre = new Rol();
		ejemploNombre.setNombre(nuevo.getNombre());
		ejemploNombre.setEmpresa(nuevo.getEmpresa());
		if (rolManager.total(ejemploNombre) > 0) {
			throw new ExceptionHornero("nombre","244", "El nombre "
					+ nuevo.getNombre() + " ya está en uso");
		}



		rolManager.crear(nuevo, userDetails.getIdFuncionario());

		meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
				"El rol se agregó con éxito", "300", ESTADO_EXITO,
				OP_ALTA, userDetails.getIdFuncionario(),
				 null, false);

		retorno.put("rol", nuevo);
		response.setStatus(201);

	} catch (Exception ex) {

		response.setStatus(422);
		meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
				"Error al agregar el rol", "300", ESTADO_ERROR,
				OP_ALTA, userDetails.getIdFuncionario(),
				 ex, false);

	}
	retorno.put("meta", meta);
	return retorno;
}

/**
 * Servicio para editar un rol
 * 
 * @param empresa
 *            la entidad Rol a modificar recibida de la vista
 */

@RequestMapping(value="/{id}", method = RequestMethod.PUT, produces = "application/json")
@ResponseBody
public HashMap<String, Object> modificarRol(@PathVariable("id") Long id,
		@RequestBody Rol rol) {

	HashMap<String, Object> retorno = new HashMap<String, Object>();
	HashMap<String, Object> meta = new HashMap<String, Object>();
	UserDetailsHornero userDetails = UserDetailsHornero
			.getFuncionarioAutenticado();

	try {

		Rol anterior = rolManager.get(id);

		rol.setId(id);

		// Controlamos que el nombre del rol no sea nulo o vacío.
		if (rol.getNombre() == null
				|| rol.getNombre().compareToIgnoreCase("") == 0) {
			throw new ExceptionHornero("nombre","243",
					"El nombre del rol no puede estar vacío");
		}

		Rol ejemploNombre = new Rol();
		ejemploNombre.setNombre(rol.getNombre());

		Map<String, Object> rolBD = rolManager.getAtributos(
				ejemploNombre, atributosRol.split(","), false, true);

		if (rolBD != null
				&& rolBD.containsKey("id")
				&& !(Long.parseLong(rolBD.get("id").toString()) == anterior
						.getId())) {
			throw new ExceptionHornero("nombre","300", "El rol con el nombre "
					+ rol.getNombre() + " ya existe");
		}


		rolManager.actualizar(rol, userDetails.getIdFuncionario());

		meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
				"El rol se modifico con éxito", "300", ESTADO_EXITO,
				OP_ALTA, userDetails.getIdFuncionario(),
				 null, false);

		retorno.put("rol", rol);

	} catch (Exception ex) {

		meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
				"Error al agregar el rol", "300", ESTADO_ERROR,
				OP_ALTA, userDetails.getIdFuncionario(),
				 ex, false);
	}
	retorno.put("meta", meta);
	return retorno;
}
}