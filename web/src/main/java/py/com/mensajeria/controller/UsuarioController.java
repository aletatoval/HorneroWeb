/**
 * 
 */
package py.com.mensajeria.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import py.com.mensajeria.model.ejb.ParametroManager;
import py.com.mensajeria.model.entity.Empresa;
import py.com.mensajeria.model.entity.Usuario;
import py.com.mensajeria.services.UserDetailsMensajeria;
import py.com.mensajeria.utils.Constantes;
import py.com.mensajeria.utils.ExceptionMensajeria;
import py.com.mensajeria.utils.utils.FiltroDTO;
import py.com.mensajeria.utils.utils.ReglaDTO;

/**
 * Controller para la entidad Usuario. Implementación de mappings, servicios
 * REST y métodos de mapeo privados y públicos.
 * 
 */
@Controller
@RequestMapping(value = "/usuarios")
public class UsuarioController extends BaseController {

	@EJB(mappedName = "java:global/mensajeriaapp-ear/mensajeriaapp-ejb/ParametroManagerImpl")
	private ParametroManager parametroManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	private String atributosUsuario = "id,nombre,alias,clave,documento,telefono,email,imei,imsi,tiempoMuestra,horaInicioMuestra"
			+ ",horaFinMuestra,rol.nombre,empresa.id,empresa.nombre,activo";

	private String permisosDesactivados = "Usuario.Activar,Usuario.Desactivar";

	/**
	 * Mapping para el metodo GET de la vista usuarioForm.
	 * 
	 */
	@RequestMapping(value = "/{id}/{accion}", method = RequestMethod.GET)
	public ModelAndView formulario(@PathVariable("id") Long id,
			@PathVariable("accion") String accion) {

		ModelAndView retorno = new ModelAndView("usuarioForm");
		retorno.addObject("editar", accion.compareToIgnoreCase("editar") == 0);
		retorno.addObject("id", id);
		return retorno;
	}

	/**
	 * Servicio REST para listar todas las Usuarios
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

		HashMap<String, Object> retorno = new HashMap<String, Object>();

		HashMap<String, Object> meta = new HashMap<String, Object>();

		UserDetailsMensajeria userDetails = UserDetailsMensajeria
				.getUsuarioAutenticado();

		Usuario ejemplo = new Usuario();
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

			ejemplo.setEmpresa(new Empresa(userDetails.getIdEmpresa()));
			ejemplo.setActivo(userDetails.getCampoActivo(permisosDesactivados));

			pagina = pagina != null ? pagina : 1;
			Integer total = 0;

			if (!todos) {
				total = usuarioManager.total(ejemplo, true, true);
			}

			Integer inicio = ((pagina - 1) < 0 ? 0 : pagina - 1) * cantidad;

			if (total < inicio) {
				inicio = total - total % cantidad;
				pagina = total / cantidad;
			}

			List<Map<String, Object>> listMapUsuarios = usuarioManager
					.listAtributos(ejemplo, atributosUsuario.split(","), todos,
							inicio, cantidad, ordenarPor.split(","),
							sentidoOrdenamiento.split(","), true, true,
							camposFiltros, valorFiltro);
			if (todos) {
				total = listMapUsuarios.size();
			}

			retorno.put("usuarios", listMapUsuarios);

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"Se listaron exitosamentes los usuarios", "300",
					ESTADO_EXITO, OP_VISUALIZACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), null, false);

			meta.put("totalDatos", total); //Puesto sólo para probar paginación, porque retornaba cero
			meta.put("pagina", pagina);

		} catch (Exception ex) {

			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al obtener la lista de empresas", "300",
					ESTADO_ERROR, OP_VISUALIZACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), ex, false);

		}

		retorno.put("meta", meta);
		return retorno;
	}

	/**
	 * Servicio REST para obtener una Usuario por id
	 * 
	 * @param id
	 *            el id del Usuario a obtener
	 */

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	HashMap<String, Object> obtener(@PathVariable("id") Long id) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		HashMap<String, Object> meta = new HashMap<String, Object>();

		UserDetailsMensajeria userDetails = UserDetailsMensajeria
				.getUsuarioAutenticado();

		try {

			Map<String, Object> usuario = usuarioManager.getAtributos(
					new Usuario(id), atributosUsuario.split(","));

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"El usuario se obtuvo exitosamente", "300", ESTADO_EXITO,
					OP_VISUALIZACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), null, false);

			retorno.put("usuario", usuario);

		} catch (Exception ex) {

			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al obtener el usuario solicitado", "300",
					ESTADO_ERROR, OP_VISUALIZACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), ex, false);

		}

		retorno.put("meta", meta);
		return retorno;

	}

	/**
	 * Mapping para el metodo POST agregar usuario
	 * 
	 * @param usuarioRecibido
	 *            la entidad Usuario a a persistir en la bd
	 */
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HashMap<String, Object> agregar(@RequestBody Usuario nuevo,  HttpServletResponse response) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		HashMap<String, Object> meta = new HashMap<String, Object>();

		UserDetailsMensajeria userDetails = UserDetailsMensajeria
				.getUsuarioAutenticado();

		logger.info("Inició correctamente el método agregar usuario por el usuario "
				+ userDetails.getIdUsuario());

		try {
			// La clave personal del usuario debe contar con una longitud mínima
			// de 7 caracteres.
			if (nuevo.getClave().length() < 7) {
				throw new ExceptionMensajeria("clave","268",
						"La Clave personal usuario debe poseer una "
								+ "longitud minima de 7 caracteres.");
			}

			// Controlamos que el documento del usuario no está ya en uso
			Usuario ejemploDocumento = new Usuario();
			ejemploDocumento.setDocumento(nuevo.getDocumento());

			if (usuarioManager.total(ejemploDocumento) > 0) {
				throw new ExceptionMensajeria("documento","270",
						"El documento del usuario ya existe");
			}

			// Controlamos que el nombre del usuario no sea nulo o vacío.
			if (nuevo.getNombre().length() == 0) {
				throw new ExceptionMensajeria("nombre","271",
						"El nombre del usuario no debe ser vacio.");
			}

			// Controlamos que la telefono del usuario no sea nulo o vacío.

			// Controlamos que la telefono del usuario no está ya en uso
			Usuario ejemploTelefono = new Usuario();
			ejemploTelefono.setTelefono(nuevo.getTelefono());

			if (usuarioManager.total(ejemploTelefono) > 0) {
				throw new ExceptionMensajeria("telefono","272",
						"La telefono del usuario ya está en uso");
			}

			// La telefono del usuario no debe poseer espacios en blanco
			if (nuevo.getTelefono().indexOf(" ") > 0) {
				throw new ExceptionMensajeria("linea","273",
						"la línea del usuario no puede contener "
								+ "espacios en blanco.");
			}

			// Controlamos que la clave del usuario no sea nulo o vacío
			if (nuevo.getClave() == null
					|| nuevo.getClave().compareToIgnoreCase("") == 0) {
				throw new ExceptionMensajeria("clave","274",
						"La clave del usuario no puede estar vacía");
			}

			// Controlamos que la clave del usuario no está ya en uso
			Usuario ejemploClave = new Usuario();
			ejemploClave.setClave(nuevo.getClave());

			if (usuarioManager.total(ejemploClave) > 0) {
				throw new ExceptionMensajeria("clave","275",
						"La clave del usuario ya está en uso");
			}

			/**
			 * A continuación se realizará un chequeo de que un usuario al
			 * agregar no exista aún en la base de datos
			 */
			Usuario ejemplo = new Usuario();
			ejemplo.setTelefono(nuevo.getTelefono());

			if (usuarioManager.total(ejemplo) > 0) {
				throw new ExceptionMensajeria(null,"276",
						"El usuario ya se encuentra en el sistema.");
			}

			nuevo.setTelefono(nuevo.getTelefono());

			// se codifica la clave utilizando como Salt el nombre del usuario
			String clavePlana = nuevo.getClave();
			String claveCodificada = passwordEncoder.encodePassword(clavePlana,
					nuevo.getTelefono());
			nuevo.setClave(claveCodificada);

			usuarioManager.crear(nuevo, userDetails.getIdUsuario());

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"Se agrego exitosamente el usuario", "300", ESTADO_EXITO,
					OP_ALTA, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), null, false);

			retorno.put("usuario", nuevo);
			response.setStatus(201);

		} catch (Exception ex) {

			response.setStatus(422);
			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Ocurrio un error al agregar el usuario", "300",
					ESTADO_ERROR, OP_ALTA, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), ex, false);

		}
		retorno.put("meta", meta);
		return retorno;
	}

	/**
	 * Mapping para el metodo POST de la vista usuaioForm. (editar Usuario)
	 * 
	 * @param usuarioRecibido
	 *            la entidad Usuario a modificar recibida de la vista
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	HashMap<String, Object> editar(@PathVariable("id") Long id,
			@RequestBody Usuario usuarioRecibido) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		HashMap<String, Object> meta = new HashMap<String, Object>();

		UserDetailsMensajeria userDetails = UserDetailsMensajeria
				.getUsuarioAutenticado();

		try {

			Usuario usuarioPersistido = usuarioManager.get(id);

			Usuario ejemplo = new Usuario();
			ejemplo.setNombre(usuarioRecibido.getNombre());
			ejemplo.setEmpresa(new Empresa(userDetails.getIdEmpresa()));

			Map<String, Object> usuarioBD = usuarioManager.getAtributos(
					ejemplo, atributosUsuario.split(","), false, true);

			if (usuarioBD != null
					&& usuarioBD.containsKey("id")
					&& !(Long.parseLong(usuarioBD.get("id").toString()) == usuarioPersistido
							.getId())) {
				throw new ExceptionMensajeria("nombre","300", "El usuario con el nombre "
						+ usuarioRecibido.getNombre() + " ya existe");
			}

			usuarioPersistido.setNombre(usuarioRecibido.getNombre());
			usuarioPersistido.setDocumento(usuarioRecibido.getDocumento());
			usuarioPersistido.setTelefono(usuarioRecibido.getTelefono());
			usuarioPersistido.setEmail(usuarioRecibido.getEmail());

			usuarioPersistido.setImsi(usuarioRecibido.getImsi());
			usuarioPersistido.setImei(usuarioRecibido.getImei());

			if (usuarioRecibido.getTiempoMuestra() != null
					&& usuarioRecibido.getTiempoMuestra() > 0) {
				usuarioPersistido.setTiempoMuestra(usuarioRecibido
						.getTiempoMuestra());
			}

			if (usuarioRecibido.getHoraInicioMuestra() != null) {
				usuarioPersistido.setHoraInicioMuestra(usuarioRecibido
						.getHoraInicioMuestra());
			}

			if (usuarioRecibido.getHoraFinMuestra() != null) {
				usuarioPersistido.setHoraFinMuestra(usuarioRecibido
						.getHoraFinMuestra());
			}

			usuarioManager.actualizar(usuarioPersistido,
					userDetails.getIdUsuario());

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"El Usuario " + usuarioRecibido.getNombre()
							+ " se modifico exitosamente", "300", ESTADO_EXITO,
					OP_MODIFICACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), null, true);

			retorno.put("usuario", usuarioPersistido);

		} catch (Exception ex) {
			meta = generarMensaje(
					meta,
					Constantes.MENSAJE_ERROR,
					"Error al modificar el usuario "
							+ usuarioRecibido.getNombre(), "300", ESTADO_ERROR,
					OP_MODIFICACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), ex, true);

		}
		retorno.put("meta", meta);
		return retorno;
	}

	/**
	 * Mapping para el metodo POST de la vista usuariosForm. (editar Usuario)
	 * 
	 * @param usuarioRecibido
	 *            la entidad Usuario a modificar recibida de la vista
	 */
	@RequestMapping(value = "/{id}/parametros", method = RequestMethod.PUT)
	public @ResponseBody
	HashMap<String, Object> actualizarParametros(
			@PathVariable("id") Long idUsuario,
			@ModelAttribute("intervalo") Integer intervaloMuestreo,
			@ModelAttribute("inicio") String horaInicio,
			@ModelAttribute("fin") String horaFin) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		UserDetailsMensajeria userDetails = UserDetailsMensajeria
				.getUsuarioAutenticado();

		try {

			Usuario usuarioPersistido = usuarioManager.get(idUsuario);

			if (usuarioPersistido == null) {
				throw new ExceptionMensajeria("telefono","500", "El telefono no existe");
			}

			if (intervaloMuestreo != null && intervaloMuestreo > 0) {
				usuarioPersistido.setTiempoMuestra(intervaloMuestreo);
			}

			if (horaInicio != null && !horaInicio.isEmpty()) {
				usuarioPersistido.setHoraInicioMuestra(horaInicio);
			}

			if (horaFin != null && !horaFin.isEmpty()) {
				usuarioPersistido.setHoraFinMuestra(horaFin);
			}

			usuarioManager.actualizar(usuarioPersistido,
					userDetails.getIdUsuario());

			retorno = generarMensaje(retorno, Constantes.MENSAJE_EXITO,
					"El Usuario " + usuarioPersistido.getNombre()
							+ " se modifico exitosamente", "300", ESTADO_EXITO,
					OP_MODIFICACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), null, true);

		} catch (Exception e) {
			retorno = generarMensaje(retorno, Constantes.MENSAJE_ERROR,
					"Error al asignar nuevos parametros", "300", ESTADO_ERROR,
					OP_MODIFICACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), e, true);
		}

		return retorno;

	}

	/**
	 * Método utilizado para modificar la clave de un usuario.
	 * 
	 * @params
	 */
	@RequestMapping(value = "/restablecerclave", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public HashMap<String, Object> restablecerClave(
			@PathVariable("id") Long idUsuario,
			@ModelAttribute("claveNueva") String claveNueva) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		UserDetailsMensajeria userDetails = UserDetailsMensajeria
				.getUsuarioAutenticado();
		String nombre = "";

		try {

			Usuario usuario = usuarioManager.get(idUsuario);

			if (usuario.getNombre() != null && !usuario.getNombre().equals("")) {
				nombre += usuario.getNombre() + " ";
			}

			// La clave personal del usuario debe contar con una longitud mínima
			// de 7 caracteres.
			if (claveNueva.length() < 7) {
				throw new ExceptionMensajeria("clave","500",
						"La nueva Clave Personal de Acceso debe poseer una "
								+ "longitud mínima de 7 caracteres.");
			}

			String clavePlana = claveNueva;
			String claveCodificada = passwordEncoder.encodePassword(clavePlana,
					usuario.getAlias());
			usuario.setClave(claveCodificada);

			usuarioManager.actualizar(usuario, userDetails.getIdUsuario());

			retorno = generarMensaje(
					retorno,
					Constantes.MENSAJE_EXITO,
					"La Clave del usuario " + nombre + " ha sido restablecida.",
					"500", ESTADO_EXITO, OP_MODIFICACION,
					userDetails.getIdUsuario(), userDetails.getIdEmpresa(),
					null, true);

		} catch (Exception ex) {
			retorno = generarMensaje(retorno, Constantes.MENSAJE_ERROR,
					"Error al restablecer clave del usuario " + nombre, "500",
					ESTADO_ERROR, OP_MODIFICACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), ex, true);
		}
		return retorno;
	}

	/**
	 * Metodo para desactivar un usuario
	 * 
	 * @param id
	 *            el id del usuario a desactivar
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	HashMap<String, Object> desactivar(@PathVariable("id") Long id) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();

		UserDetailsMensajeria userDetails = UserDetailsMensajeria
				.getUsuarioAutenticado();
		try {
			Map<String, Object> usuarioBD = usuarioManager.getAtributos(
					new Usuario(id), atributosUsuario.split(","), false, true);

			if (usuarioBD != null && usuarioBD.get("activo").equals("N")) {
				throw new ExceptionMensajeria(null,"300",
						"El usuario ya se encuentra desactivado");
			}

			usuarioManager.desactivar(id, userDetails.getIdUsuario());

			retorno = generarMensaje(retorno, Constantes.MENSAJE_EXITO,
					"El usuario se desactivo exitosamente", "300",
					ESTADO_EXITO, OP_DESACTIVACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), null, true);

		} catch (Exception ex) {

			retorno = generarMensaje(retorno, Constantes.MENSAJE_ERROR,
					"Error al desactivar el usuario", "300", ESTADO_ERROR,
					OP_DESACTIVACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), ex, true);

		}

		return retorno;
	}

	/**
	 * Metodo para activar un usuario
	 * 
	 * @param id
	 *            el id del usuario a activar
	 */
	@RequestMapping(value = "/{id}/activar", method = RequestMethod.PUT)
	public @ResponseBody
	HashMap<String, Object> activar(@PathVariable("id") Long id) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();

		UserDetailsMensajeria userDetails = UserDetailsMensajeria
				.getUsuarioAutenticado();
		try {
			Map<String, Object> usuarioBD = usuarioManager.getAtributos(
					new Usuario(id), atributosUsuario.split(","), false, true);

			if (usuarioBD != null && usuarioBD.get("activo").equals("S")) {
				throw new ExceptionMensajeria(null,"300",
						"El usuario ya se encuentra activado");
			}

			usuarioManager.activar(id, userDetails.getIdUsuario());

			retorno = generarMensaje(retorno, Constantes.MENSAJE_EXITO,
					"El usuario se activo exitosamente", "300", ESTADO_EXITO,
					OP_DESACTIVACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), null, true);

		} catch (Exception ex) {

			retorno = generarMensaje(retorno, Constantes.MENSAJE_ERROR,
					"Error al activar el usuario", "300", ESTADO_ERROR,
					OP_DESACTIVACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), ex, true);

		}

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
	public static Usuario generarEjemplo(FiltroDTO filtro, Usuario ejemplo)
			throws Exception {

		if (ejemplo == null) {
			ejemplo = new Usuario();
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
