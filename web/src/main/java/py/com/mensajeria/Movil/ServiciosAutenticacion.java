package py.com.mensajeria.Movil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import py.com.mensajeria.controller.BaseController;
import py.com.mensajeria.model.ejb.RolPermisoManager;
import py.com.mensajeria.model.entity.Usuario;
import py.com.mensajeria.utils.Constantes;
import py.com.mensajeria.utils.ExceptionMensajeria;
import py.com.mensajeria.utils.Respuesta;
import py.com.mensajeria.utils.utils.RespuestaLista;

@Controller
public class ServiciosAutenticacion extends BaseController {

	@EJB(mappedName = "java:global/mensajeriaapp-ear/mensajeriaapp-ejb/RolPermisoManagerImpl")
	private RolPermisoManager rolPermisoManager;

	@RequestMapping(value = "/movil/autenticar", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Respuesta<Usuario> autenticarUsuario(@RequestBody String user) {
		
		Respuesta<Usuario> respuesta = new Respuesta<Usuario>();
		
		Usuario usuario = new Usuario();
		Map<String, Object> usuarioRes = new HashMap<String, Object>();
		Map<String, Object> validarUsuario = null;
		HashMap<String, Object> meta = new HashMap<String, Object>();

		try {

			validarUsuario = usuarioManager.obtenerUsuarioLogueado(user,
							"id,nombre,alias,documento,activo,clave,rol.id,"
							+ "empresa.id,empresa.nombre,empresa.alias,empresa.activo,tiempoMuestra,horaInicioMuestra,horaFinMuestra");
			
			usuarioRes = (Map<String, Object>) validarUsuario.get("resultado");
			
			usuario = usuarioManager.get(new Usuario(Long.parseLong(usuarioRes.get("id").toString())));
			usuario.setClave(null);

			respuesta.setError(false);
            respuesta.setValido(true);
            respuesta.setResultado(usuario);
			respuesta.setMensaje("Login Exitoso, Bienvenido");

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					(String) validarUsuario.get("mensaje"), "", ESTADO_EXITO,
					OP_LOGIN, Long.parseLong(usuarioRes.get("id").toString()),
					Long.parseLong(usuarioRes.get("empresa.id").toString()),
					null, true);
			meta.put("error", validarUsuario.get("error"));
			meta.put("codigoError", validarUsuario.get("codError"));

		} catch (Exception ex) {

			Long idUsuario = null;
			Long idEmpresa = null;

			if (usuarioRes != null && usuarioRes.containsKey("id")) {
				idUsuario = Long.parseLong(usuarioRes.get("id").toString());
				if (usuarioRes.containsKey("empresa.id")) {
					idEmpresa = Long.parseLong(usuarioRes.get("empresa.id")
							.toString());
				}
			}

			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al loguerse", "100", ESTADO_ERROR, OP_LOGIN,
					idUsuario, idEmpresa, ex, true);

		}
		return respuesta;
	}

	/**
	 * Retorna las Etiquetas para de un * obtenido a partir del para
	 * {linea,clave}
	 * 
	 * @return
	 */
	@RequestMapping(value = "/movil/descargarPermisos", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public RespuestaLista<String> descargarPermisos(@RequestBody String entidad) {

		RespuestaLista<String> respuesta = new RespuestaLista<String>();
		// RespuestaLista<String> respuestaPermiso = new
		// RespuestaLista<String>();
		List<String> permisosTangerine = null;
		Map<String, Object> userDetails = null;

		try {

			userDetails = usuarioManager.obtenerUsuarioLogueado(entidad, null);

			// TODO servicios para listar permisos
			// respuestaPermiso =
			// ServiciosCaosController.getInstance().listarPermiso(
			// userDetails.get("empresa.alias").toString(),
			// userDetails.get("nombreRol").toString());

			permisosTangerine = rolPermisoManager.listarPermisos(
					(Long) userDetails.get("empresa.id"),
					(Long) userDetails.get("rol.id"));

			if (permisosTangerine == null || permisosTangerine.size() <= 0
					|| permisosTangerine.isEmpty()) {
				throw new ExceptionMensajeria(null,"", "El usuario "
						+ userDetails.get("alias").toString()
						+ " no posee permisos para esta aplicación");
			}

			respuesta.setResultado(permisosTangerine);
			respuesta.setTotalRegistros(permisosTangerine.size());
			respuesta.setError(false);
			respuesta.setValido(Boolean.TRUE);
			respuesta.setMensaje("Operación descarga de permisos exitosa.");
			return respuesta;

		} catch (Exception ex) {

			Long idUsuario = null;
			Long idEmpresa = null;

			if (userDetails != null) {
				idUsuario = Long.parseLong(userDetails.get("id").toString());
				idEmpresa = Long.parseLong(userDetails.get("empresa.id")
						.toString());
			}

			respuesta.setError(true);
			respuesta.setValido(false);
			respuesta.setTotalRegistros(0);
			respuesta.setMensaje("Error al descargar los Permisos");

			// respuesta.setMensaje(generarMensaje(null,
			// Constantes.MENSAJE_ERROR,
			// "Error al descargar los Permisos", "100", ESTADO_ERROR,
			// OP_SINCRONIZACION, idUsuario, idEmpresa, null, true)
			// .getMensaje());

			generarMensajeSinRetorno(Constantes.MENSAJE_ERROR,
					"Error al descargar los Permisos", "100", ESTADO_ERROR,
					OP_SINCRONIZACION, idUsuario, idEmpresa, null, true);

			return respuesta;
		}
	}

}
