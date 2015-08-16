/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.hornero.controller;

import static py.com.hornero.utils.utils.Utils.generarClaveAleatoria;

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

import py.com.hornero.model.ejb.EmpresaManager;
import py.com.hornero.model.ejb.RolManager;
import py.com.hornero.model.entity.Empresa;
import py.com.hornero.model.entity.Rol;
import py.com.hornero.services.UserDetailsHornero;
import py.com.hornero.utils.Constantes;
import py.com.hornero.utils.ExceptionHornero;
import py.com.hornero.utils.utils.FiltroDTO;
import py.com.hornero.utils.utils.ReglaDTO;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/empresas")
public class EmpresaController extends BaseController {

//	private String atributosEmpresa = "id,activo,nombre,alias,ruc,codigoAcceso,descripcion,direccion"
//			+ ",telefono,email,soporteNombre,soporteTelefono,soporteEmail";
//
//	@EJB(mappedName = "java:global/horneroapp-ear/horneroapp-ejb/EmpresaManagerImpl")
//	private EmpresaManager empresaManager;
//	
//	
//	@EJB(mappedName = "java:global/horneroapp-ear/horneroapp-ejb/RolManagerImpl")
//	private RolManager rolManager;
//
//	/**
//	 * Servicio REST para listar todas las empresas
//	 * 
//	 */
//	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
//	public @ResponseBody
//	HashMap<String, Object> lista(@ModelAttribute("_search") boolean filtrar,
//			@ModelAttribute("filters") String filtros,
//			@ModelAttribute("page") Integer pagina,
//			@ModelAttribute("rows") Integer cantidad,
//			@ModelAttribute("sidx") String ordenarPor,
//			@ModelAttribute("sord") String sentidoOrdenamiento,
//			@ModelAttribute("todos") boolean todos,
//			@ModelAttribute("idEmpresa") String idEmpresa) {
//
//		// map para el retorno que contendra la lista y los metadatos
//		HashMap<String, Object> retorno = new HashMap<String, Object>();
//
//		// map para los metadatos del retorno
//		HashMap<String, Object> meta = new HashMap<String, Object>();
//
//		UserDetailsHornero userDetails = UserDetailsHornero
//				.getUsuarioAutenticado();
//
//		Empresa ejemplo = new Empresa();
//		try {
//
//			Gson gson = new Gson();
//			String camposFiltros = null;
//			String valorFiltro = null;
//
//			if (filtrar) {
//				FiltroDTO filtro = gson.fromJson(filtros, FiltroDTO.class);
//				if (filtro.getGroupOp().compareToIgnoreCase(Constantes.OP_OR) == 0) {
//					for (ReglaDTO regla : filtro.getRules()) {
//						if (camposFiltros == null) {
//							camposFiltros = regla.getField();
//							valorFiltro = regla.getData();
//						} else {
//							camposFiltros += "," + regla.getField();
//						}
//					}
//				} else {
//					ejemplo = generarEjemplo(filtro, ejemplo);
//				}
//
//			}
//			
//
//			pagina = pagina != null ? pagina : 1;
//			Integer total = 0;
//
//			if (!todos) {
//				total = empresaManager.total(ejemplo, false, false);
//			}
//
//			Integer inicio = ((pagina - 1) < 0 ? 0 : pagina - 1) * cantidad;
//
//			if (total < inicio) {
//				inicio = total - total % cantidad;
//				pagina = total / cantidad;
//			}
//
//			List<Map<String, Object>> listMapEmpresas = empresaManager
//					.listAtributos(ejemplo, atributosEmpresa.split(","), todos,
//							inicio, cantidad, ordenarPor.split(","),
//							sentidoOrdenamiento.split(","), true, true,
//							camposFiltros, valorFiltro);
//		
//			if (todos) {
//				total = listMapEmpresas.size();
//			}
//
//			retorno.put("empresas", listMapEmpresas);
//
//			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
//					"Se listaron exitosamentes las empresas", "300",
//					ESTADO_EXITO, OP_VISUALIZACION, userDetails.getIdUsuario(),
//					userDetails.getIdEmpresa(), null, false);
//
//			meta.put("totalDatos", total);
//			meta.put("pagina", pagina);
//
//		} catch (Exception ex) {
//
//			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
//					"Error al obtener la lista de empresas", "300",
//					ESTADO_ERROR, OP_VISUALIZACION, userDetails.getIdUsuario(),
//					userDetails.getIdEmpresa(), ex, false);
//		}
//
//		retorno.put("meta", meta);
//		return retorno;
//
//	}
//
//	/**
//	 * Servicio REST para obtener una empresa por id
//	 * 
//	 * @param id
//	 *            el id de la empresa a obtener
//	 */
//
//	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
//	public @ResponseBody
//	HashMap<String, Object> obtener(@PathVariable("id") Long id) {
//
//		HashMap<String, Object> retorno = new HashMap<String, Object>();
//		HashMap<String, Object> meta = new HashMap<String, Object>();
//		UserDetailsHornero userDetails = UserDetailsHornero
//				.getUsuarioAutenticado();
//
//		try {
//
//			Map<String, Object> empresa = empresaManager.getAtributos(
//					new Empresa(id), atributosEmpresa.split(","));
//			
//			//Lista de roles asignados a la empresa
//			List<Long> misRoles = rolManager.misIdRoles(Long.valueOf(empresa.get("id").toString()), null, null, null, null);
//			empresa.put("roles", misRoles);
//			
//
//			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
//					"El usuario se obtuvo exitosamente", "300", ESTADO_EXITO,
//					OP_VISUALIZACION, userDetails.getIdUsuario(),
//					userDetails.getIdEmpresa(), null, false);
//
//			retorno.put("empresa", empresa);
//
//		} catch (Exception ex) {
//
//			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
//					"Error al obtener la empresa solicitada", "300",
//					ESTADO_ERROR, OP_VISUALIZACION, userDetails.getIdUsuario(),
//					userDetails.getIdEmpresa(), ex, false);
//
//		}
//
//		retorno.put("meta", meta);
//		return retorno;
//
//	}
//
//	/**
//	 * Servicio REST para persistir una nueva empresa
//	 * 
//	 * @param nuevo
//	 *            entidad a persistir
//	 */
//
//	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
//	@ResponseBody
//	public HashMap<String, Object> agregarEmpresa(@RequestBody Empresa nuevo,  HttpServletResponse response) {
//
//		HashMap<String, Object> retorno = new HashMap<String, Object>();
//		HashMap<String, Object> meta = new HashMap<String, Object>();
//		UserDetailsHornero userDetails = UserDetailsHornero
//				.getUsuarioAutenticado();
//
//		logger.info("Inició correctamente el método agregar empresas por el usuario "
//				+ userDetails.getNombreCompleto());
//
//		try {
//			// Controlamos que el nombre de la empresa no sea nulo o vacío.
//			if (nuevo.getNombre() == null
//					|| nuevo.getNombre().compareToIgnoreCase("") == 0) {
//				throw new ExceptionHornero("nombre","243",
//						"El nombre de la empresa no puede estar vacío");
//			}
//
//			// Controlamos que el nombre de la empresa no está ya en uso.
//			Empresa ejemploNombre = new Empresa();
//			ejemploNombre.setNombre(nuevo.getNombre());
//
//			if (empresaManager.total(ejemploNombre) > 0) {
//				throw new ExceptionHornero("nombre","244", "El nombre "
//						+ nuevo.getNombre() + " ya está en uso");
//			}
//
//			// Controlamos que el alias de la empresa no sea nulo o vacío.
//			if (nuevo.getAlias() == null
//					|| nuevo.getAlias().compareToIgnoreCase("") == 0) {
//				throw new ExceptionHornero("alias","245",
//						"El alias de la empresa no puede estar vacío");
//			}
//
//			// Controlamos que el nombre de la empresa no está ya en uso.
//			Empresa ejemploAlias = new Empresa();
//			ejemploAlias.setAlias(nuevo.getAlias());
//
//			if (empresaManager.total(ejemploAlias) > 0) {
//				throw new ExceptionHornero("alias","246", "El alias "
//						+ nuevo.getAlias() + " ya está en uso");
//			}
//
//			// Controlamos que el RUC de la empresa no sea nulo o vacío
//			if (nuevo.getRuc() == null
//					|| nuevo.getRuc().compareToIgnoreCase("") == 0) {
//				throw new ExceptionHornero("ruc","247",
//						"El RUC de la empresa no puede estar vacío");
//			}
//
//			// Controlamos que el RUC de la empresa no está ya en uso
//			Empresa ejemploRuc = new Empresa();
//			ejemploRuc.setRuc(nuevo.getRuc());
//
//			if (empresaManager.total(ejemploRuc) > 0) {
//				throw new ExceptionHornero("ruc","248", "El RUC " + nuevo.getRuc()
//						+ " de la empresa ya esta asociado a otra Empresa");
//			}
//
//			// le asignamos un codigo de acceso
//			nuevo.setCodigoAcceso(generarClaveAleatoria(userDetails
//					.getIdEmpresa()));
//
//			empresaManager.crear(nuevo, userDetails.getIdUsuario());
//
//			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
//					"La empresa se agregó con éxito", "300", ESTADO_EXITO,
//					OP_ALTA, userDetails.getIdUsuario(),
//					userDetails.getIdEmpresa(), null, false);
//
//			retorno.put("empresa", nuevo);
//			response.setStatus(201);
//			response.setContentType("La empresa se agregó con éxito");
//
//		} catch (Exception ex) {
//
//			response.setStatus(422);
//			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
//					"Error al agregar la empresa", "300", ESTADO_ERROR,
//					OP_ALTA, userDetails.getIdUsuario(),
//					userDetails.getIdEmpresa(), ex, false);
//
//		}
//		retorno.put("meta", meta);
//		return retorno;
//	}
//
//	/**
//	 * Servicio para editar una empresa
//	 * 
//	 * @param empresa
//	 *            la entidad Empresa a modificar recibida de la vista
//	 */
//
//	@RequestMapping(value="/{id}", method = RequestMethod.PUT, produces = "application/json")
//	@ResponseBody
//	public HashMap<String, Object> modificarEmpresa(@PathVariable("id") Long id,
//			@RequestBody Empresa empresa) {
//
//		HashMap<String, Object> retorno = new HashMap<String, Object>();
//		HashMap<String, Object> meta = new HashMap<String, Object>();
//		UserDetailsHornero userDetails = UserDetailsHornero
//				.getUsuarioAutenticado();
//
//		try {
//
//			Empresa anterior = empresaManager.get(id);
//
//			empresa.setFechaCreacion(anterior.getFechaCreacion());
//			empresa.setIdUsuarioCreacion(anterior.getIdUsuarioCreacion());
//			empresa.setId(id);
//
//			// Controlamos que el nombre de la empresa no sea nulo o vacío.
//			if (empresa.getNombre() == null
//					|| empresa.getNombre().compareToIgnoreCase("") == 0) {
//				throw new ExceptionHornero("nombre","243",
//						"El nombre de la empresa no puede estar vacío");
//			}
//
//			Empresa ejemploNombre = new Empresa();
//			ejemploNombre.setNombre(empresa.getNombre());
//
//			Map<String, Object> empresaBD = empresaManager.getAtributos(
//					ejemploNombre, atributosEmpresa.split(","), false, true);
//
//			if (empresaBD != null
//					&& empresaBD.containsKey("id")
//					&& !(Long.parseLong(empresaBD.get("id").toString()) == anterior
//							.getId())) {
//				throw new ExceptionHornero("nombre","300", "La empresa con el nombre "
//						+ empresa.getNombre() + " ya existe");
//			}
//
//			if (empresa.getAlias() == null
//					|| empresa.getAlias().compareToIgnoreCase("") == 0) {
//				throw new ExceptionHornero("alias","245",
//						"El alias de la empresa no puede estar vacío");
//			}
//
//			Empresa ejemploAlias = new Empresa();
//			ejemploAlias.setNombre(empresa.getNombre());
//
//			Map<String, Object> empresaAlias = empresaManager.getAtributos(
//					ejemploAlias, atributosEmpresa.split(","), false, true);
//
//			if (empresaAlias != null
//					&& empresaAlias.containsKey("id")
//					&& !(Long.parseLong(empresaAlias.get("id").toString()) == anterior
//							.getId())) {
//				throw new ExceptionHornero("alias","300", "El alias "
//						+ empresa.getAlias() + " ya existe");
//			}
//
//			// Controlamos que el RUC de la empresa no sea nulo o vacío
//			if (empresa.getRuc() == null
//					|| empresa.getRuc().compareToIgnoreCase("") == 0) {
//				throw new ExceptionHornero("ruc","247",
//						"El RUC de la empresa no puede estar vacío");
//			}
//
//			Empresa ejemploRuc = new Empresa();
//			ejemploRuc.setNombre(empresa.getNombre());
//
//			Map<String, Object> empresaRuc = empresaManager.getAtributos(
//					ejemploRuc, atributosEmpresa.split(","), false, true);
//
//			if (empresaRuc != null
//					&& empresaRuc.containsKey("id")
//					&& !(Long.parseLong(empresaRuc.get("id").toString()) == anterior
//							.getId())) {
//				throw new ExceptionHornero("ruc","300", "El ruc "
//						+ empresa.getRuc() + " ya existe");
//			}
//
//			empresaManager.actualizar(empresa, userDetails.getIdUsuario());
//
//			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
//					"La empresa se modifico con éxito", "300", ESTADO_EXITO,
//					OP_ALTA, userDetails.getIdUsuario(),
//					userDetails.getIdEmpresa(), null, false);
//
//			retorno.put("empresa", empresa);
//
//		} catch (Exception ex) {
//
//			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
//					"Error al agregar la empresa", "300", ESTADO_ERROR,
//					OP_ALTA, userDetails.getIdUsuario(),
//					userDetails.getIdEmpresa(), ex, false);
//		}
//		retorno.put("meta", meta);
//		return retorno;
//	}
//	
//
//	/**
//	 * Metodo para desactivar una empresa
//	 * 
//	 * @param id
//	 *            el id de la empresa a desactivar
//	 */
//	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//	public @ResponseBody
//	HashMap<String, Object> desactivar(@PathVariable("id") Long id) {
//
//		HashMap<String, Object> retorno = new HashMap<String, Object>();
//
//		UserDetailsHornero userDetails = UserDetailsHornero
//				.getUsuarioAutenticado();
//		try {
//			Map<String, Object> empresaBD = empresaManager.getAtributos(
//					new Empresa(id), atributosEmpresa.split(","), false, true);
//
//			if (empresaBD != null && empresaBD.get("activo").equals("N")) {
//				throw new ExceptionHornero(null,"300",
//						"La empresa ya se encuentra desactivada");
//			}
//
//			empresaManager.desactivar(id, userDetails.getIdUsuario());
//
//			retorno = generarMensaje(retorno, Constantes.MENSAJE_EXITO,
//					"La empresa se desactivo exitosamente", "300",
//					ESTADO_EXITO, OP_DESACTIVACION, userDetails.getIdUsuario(),
//					userDetails.getIdEmpresa(), null, true);
//
//		} catch (Exception ex) {
//
//			retorno = generarMensaje(retorno, Constantes.MENSAJE_ERROR,
//					"Error al desactivar la empresa", "300", ESTADO_ERROR,
//					OP_DESACTIVACION, userDetails.getIdUsuario(),
//					userDetails.getIdEmpresa(), ex, true);
//
//		}
//
//		return retorno;
//	}
//
//	/**
//	 * Metodo para activar una empresa
//	 * 
//	 * @param id
//	 *            el id de la empresa a activar
//	 */
//	@RequestMapping(value = "/{id}/activar", method = RequestMethod.PUT)
//	public @ResponseBody
//	HashMap<String, Object> activar(@PathVariable("id") Long id) {
//
//		HashMap<String, Object> retorno = new HashMap<String, Object>();
//
//		UserDetailsHornero userDetails = UserDetailsHornero
//				.getUsuarioAutenticado();
//		try {
//			Map<String, Object> empresaBD = empresaManager.getAtributos(
//					new Empresa(id), atributosEmpresa.split(","), false, true);
//
//			if (empresaBD != null && empresaBD.get("activo").equals("S")) {
//				throw new ExceptionHornero(null,"300",
//						"La empresa ya se encuentra activada");
//			}
//
//			empresaManager.activar(id, userDetails.getIdUsuario());
//
//			retorno = generarMensaje(retorno, Constantes.MENSAJE_EXITO,
//					"La empresa se activo exitosamente", "300", ESTADO_EXITO,
//					OP_DESACTIVACION, userDetails.getIdUsuario(),
//					userDetails.getIdEmpresa(), null, true);
//
//		} catch (Exception ex) {
//
//			retorno = generarMensaje(retorno, Constantes.MENSAJE_ERROR,
//					"Error al activar la empresa", "300", ESTADO_ERROR,
//					OP_DESACTIVACION, userDetails.getIdUsuario(),
//					userDetails.getIdEmpresa(), ex, true);
//
//		}
//
//		return retorno;
//	}
//
//	/**
//	 * Recibe un string que es un json y devuelve una entidad con los valores
//	 * recibidos
//	 * 
//	 * @param filtros
//	 * @return
//	 * @throws Exception
//	 */
//	public static Empresa generarEjemplo(FiltroDTO filtro, Empresa ejemplo)
//			throws Exception {
//
//		if (ejemplo == null) {
//			ejemplo = new Empresa();
//		}
//
//		for (ReglaDTO regla : filtro.getRules()) {
//
//			if ("id".compareToIgnoreCase(regla.getField()) == 0
//					&& regla.getData() != null && !regla.getData().isEmpty()) {
//				ejemplo.setId(Long.parseLong(regla.getData()));
//
//			} else if ("nombre".compareToIgnoreCase(regla.getField()) == 0
//					&& regla.getData() != null && !regla.getData().isEmpty()) {
//				ejemplo.setNombre(regla.getData());
//
//			}
//		}
//		return ejemplo;
//	}
//
//	/**
//	 * Servicio REST para generar/modificar clave de acceso
//	 * 
//	 */
//	@RequestMapping(value = "/clave", method = RequestMethod.GET, produces = "application/json")
//	public @ResponseBody
//	String claveNueva() {
//
//		UserDetailsHornero userDetails = UserDetailsHornero
//				.getUsuarioAutenticado();
//
//		String clave = generarClaveAleatoria(userDetails.getIdEmpresa());
//
//		return clave;
//	}
//	
//	/**
//	 * Servicio REST para persistir un nuevo rol y asignar a la empresa
//	 * 
//	 * @param nuevo
//	 *            entidad a persistir
//	 */
//
//	@RequestMapping(value="/{id}/roles",method = RequestMethod.POST, produces = "application/json")
//	@ResponseBody
//	public HashMap<String, Object> agregarRol(@PathVariable("id") Long idEmpresa,
//			@RequestBody Rol nuevo,  HttpServletResponse response) {
//
//		HashMap<String, Object> retorno = new HashMap<String, Object>();
//		HashMap<String, Object> meta = new HashMap<String, Object>();
//		UserDetailsHornero userDetails = UserDetailsHornero
//				.getUsuarioAutenticado();
//
//		logger.info("Inició correctamente el método agregar rol por el usuario "
//				+ userDetails.getNombreCompleto());
//
//		try {
//			// Controlamos que el nombre del rol no sea nulo o vacío.
//			if (nuevo.getNombre() == null
//					|| nuevo.getNombre().compareToIgnoreCase("") == 0) {
//				throw new ExceptionHornero("nombre","243",
//						"El nombre del rol no puede estar vacío");
//			}
//
//			// Controlamos que el nombre del rol no está ya en uso.
//			Rol rol = new Rol();
//			rol.setNombre(nuevo.getNombre());
//
//			if (rolManager.total(rol) > 0) {
//				throw new ExceptionHornero("nombre","244", "El nombre "
//						+ nuevo.getNombre() + " ya está en uso");
//			}
//
//
//			//Asignar el rol a la empresa 
//			rol.setEmpresa(new Empresa(idEmpresa));
//			rol = rolManager.crear(nuevo, userDetails.getIdUsuario());
//			
//			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
//					"El rol se agregó con éxito", "300", ESTADO_EXITO,
//					OP_ALTA, userDetails.getIdUsuario(),
//					userDetails.getIdEmpresa(), null, false);
//
//			retorno.put("rol", rol);
//			response.setStatus(201);
//
//		} catch (Exception ex) {
//
//			response.setStatus(422);
//			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
//					"Error al agregar el rol", "300", ESTADO_ERROR,
//					OP_ALTA, userDetails.getIdUsuario(),
//					userDetails.getIdEmpresa(), ex, false);
//
//		}
//		retorno.put("meta", meta);
//		return retorno;
//	}

}
