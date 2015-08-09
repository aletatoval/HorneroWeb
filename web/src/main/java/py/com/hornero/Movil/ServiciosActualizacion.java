package py.com.hornero.Movil;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import py.com.hornero.controller.BaseController;
import py.com.hornero.model.ejb.LocalizacionManager;
import py.com.hornero.model.entity.Empresa;
import py.com.hornero.model.entity.Localizacion;
import py.com.hornero.model.entity.Usuario;
import py.com.hornero.utils.Constantes;
import py.com.hornero.utils.Respuesta;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class ServiciosActualizacion extends BaseController {

	@EJB(mappedName = "java:global/horneroapp-ear/horneroapp-ejb/LocalizacionManagerImpl")
	private LocalizacionManager localizacionManager;

	@RequestMapping(value = "/movil/guardarLocalizacion", produces = "application/json", method = RequestMethod.POST)
	@ResponseBody
	public Respuesta<Usuario> guardarLocalizacion(
			@RequestBody String entidad) {


		Map<String, Object> userDetails = null;
		Map<String, Object> retornoValidacion = new HashMap<String, Object>();
		Respuesta<Usuario> respuesta = new Respuesta<Usuario>();
		
		try {

			Gson gson = new GsonBuilder().setDateFormat(
					"yyyy-MM-dd HH:mm:ss.SSS").create();

			// Gson gson = new Gson();
			retornoValidacion = usuarioManager.obtenerUsuarioLogueado(entidad,
							"id,nombre,alias,documento,activo,clave,rol.id,"
									+ "empresa.id,empresa.nombre,empresa.alias,empresa.activo,tiempoMuestra,horaInicioMuestra,horaFinMuestra");

			userDetails = (Map<String, Object>) retornoValidacion.get("resultado");
			userDetails.remove("clave");
			
			Gson gsonUser = new GsonBuilder().create();	
			
			Usuario aEnviar = gsonUser.fromJson(gsonUser.toJson(userDetails), Usuario.class);
			respuesta.setResultado(aEnviar);

			Localizacion localizacion = new Localizacion();
			localizacion = gson.fromJson(entidad, Localizacion.class);

			localizacion.setUsuario(new Usuario(Long.parseLong(userDetails.get(
					"id").toString())));
			localizacion.setEmpresa(new Empresa(Long.parseLong(userDetails.get(
					"empresa.id").toString())));
			localizacion.setFechaSincronizacion(new Timestamp(System
					.currentTimeMillis()));

			if (localizacion.getId() == null) {
				localizacionManager.crear(localizacion, Long.valueOf(userDetails.get("id").toString()));

			} else {
				localizacionManager.actualizar(localizacion, Long.valueOf(userDetails.get("id").toString()));
			}

//			meta.put("error", false);
//			meta.put("mensaje", "La Localización se guardo exitosamente");
//			meta.put("tipoMensaje", ESTADO_EXITO);
			respuesta.setError(false);
			respuesta.setValido(true);
			respuesta.setMensaje("La Localización se guardo exitosamente");

		} catch (Exception ex) {

			Long idEmpresa = null;
			Long idUsuario = null;
			if (userDetails != null) {
				idEmpresa = Long.parseLong(userDetails.get("empresa.id")
						.toString());
				idUsuario = Long.parseLong(userDetails.get("id").toString());
			}
			respuesta.setError(true);
			respuesta.setValido(false);
			respuesta.setMensaje(generarMensaje(null,Constantes.MENSAJE_ERROR,
					"Error al guardar la localización de la línea " + idUsuario,
					"100", ESTADO_ERROR, OP_SINCRONIZACION, idUsuario,idEmpresa, ex, true).get(0).toString()); 
		}

		return respuesta;
	}

}
