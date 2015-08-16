package py.com.hornero.Movil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import py.com.hornero.controller.BaseController;
import py.com.hornero.model.ejb.RolPermisoManager;
import py.com.hornero.model.entity.Funcionario;
import py.com.hornero.utils.Constantes;
import py.com.hornero.utils.ExceptionHornero;
import py.com.hornero.utils.Respuesta;
import py.com.hornero.utils.utils.RespuestaLista;

@Controller
public class ServiciosAutenticacion extends BaseController {

	@EJB(mappedName = "java:global/horneroapp-ear/horneroapp-ejb/RolPermisoManagerImpl")
	private RolPermisoManager rolPermisoManager;

	@RequestMapping(value = "/movil/autenticar", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Respuesta<Funcionario> autenticarFuncionario(@RequestBody String user) {
		
		Respuesta<Funcionario> respuesta = new Respuesta<Funcionario>();
		
		Funcionario funcionario = new Funcionario();
		Map<String, Object> funcionarioRes = new HashMap<String, Object>();
		Map<String, Object> validarFuncionario = null;
		HashMap<String, Object> meta = new HashMap<String, Object>();

		try {

			validarFuncionario = funcionarioManager.obtenerFuncionarioLogueado(user,
							"id,nombre,alias,documento,activo,clave,rol.id,"
							+ "empresa.id,empresa.nombre,empresa.alias,empresa.activo,tiempoMuestra,horaInicioMuestra,horaFinMuestra");
			
			funcionarioRes = (Map<String, Object>) validarFuncionario.get("resultado");
			
			funcionario = funcionarioManager.get(new Funcionario(Long.parseLong(funcionarioRes.get("id").toString())));
			funcionario.setContraseña(null);

			respuesta.setError(false);
            respuesta.setValido(true);
            respuesta.setResultado(funcionario);
			respuesta.setMensaje("Login Exitoso, Bienvenido");

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					(String) validarFuncionario.get("mensaje"), "", ESTADO_EXITO,
					OP_LOGIN, Long.parseLong(funcionarioRes.get("id").toString()),
					null, true);
			meta.put("error", validarFuncionario.get("error"));
			meta.put("codigoError", validarFuncionario.get("codError"));

		} catch (Exception ex) {

			Long idFuncionario = null;
			Long idEmpresa = null;

			if (funcionarioRes != null && funcionarioRes.containsKey("id")) {
				idFuncionario = Long.parseLong(funcionarioRes.get("id").toString());
				if (funcionarioRes.containsKey("empresa.id")) {
					idEmpresa = Long.parseLong(funcionarioRes.get("empresa.id")
							.toString());
				}
			}

			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al loguerse", "100", ESTADO_ERROR, OP_LOGIN,
					idFuncionario, ex, true);

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
		List<String> permisosHornero = null;
		Map<String, Object> userDetails = null;

		try {

			userDetails = funcionarioManager.obtenerFuncionarioLogueado(entidad, null);

			// TODO servicios para listar permisos
			// respuestaPermiso =
			// ServiciosCaosController.getInstance().listarPermiso(
			// userDetails.get("empresa.alias").toString(),
			// userDetails.get("nombreRol").toString());

			permisosHornero = rolPermisoManager.listarPermisos(
					(Long) userDetails.get("empresa.id"),
					(Long) userDetails.get("rol.id"));

			if (permisosHornero == null || permisosHornero.size() <= 0
					|| permisosHornero.isEmpty()) {
				throw new ExceptionHornero(null,"", "El funcionario "
						+ userDetails.get("alias").toString()
						+ " no posee permisos para esta aplicación");
			}

			respuesta.setResultado(permisosHornero);
			respuesta.setTotalRegistros(permisosHornero.size());
			respuesta.setError(false);
			respuesta.setValido(Boolean.TRUE);
			respuesta.setMensaje("Operación descarga de permisos exitosa.");
			return respuesta;

		} catch (Exception ex) {

			Long idFuncionario = null;
			Long idEmpresa = null;

			if (userDetails != null) {
				idFuncionario = Long.parseLong(userDetails.get("id").toString());
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
			// OP_SINCRONIZACION, idFuncionario, idEmpresa, null, true)
			// .getMensaje());

			generarMensajeSinRetorno(Constantes.MENSAJE_ERROR,
					"Error al descargar los Permisos", "100", ESTADO_ERROR,
					OP_SINCRONIZACION, idFuncionario,  null, true);

			return respuesta;
		}
	}

}
