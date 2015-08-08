/**
 * 
 */
package py.com.mensajeria.controller;

import java.util.HashMap;

import javax.ejb.EJB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import py.com.mensajeria.model.ejb.ParametroManager;
import py.com.mensajeria.model.ejb.RolPermisoManager;
import py.com.mensajeria.model.entity.Usuario;
import py.com.mensajeria.services.UserDetailsMensajeria;
//import py.com.tangerine.root.ServiciosCaosController;
import py.com.mensajeria.utils.Constantes;
import py.com.mensajeria.utils.ExceptionMensajeria;
import py.com.mensajeria.utils.json.JSONObject;

/**
 * @author Hermann Bottger
 * 
 */
@Controller
@RequestMapping(value = "/")
public class LoginController extends BaseController {

	@EJB(mappedName = "java:global/mensajeriaapp-ear/mensajeriaapp-ejb/ParametroManagerImpl")
	private ParametroManager parametroManager;

	@EJB(mappedName = "java:global/mensajeriaapp-ear/mensajeriaapp-ejb/RolPermisoManagerImpl")
	private RolPermisoManager rolPermisoManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView root(Model modele) {

		return new ModelAndView("index");
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = "login/fallo", method = RequestMethod.GET)
	public String loginFallo(Model model) {
		model.addAttribute("error", true);
		model.addAttribute("mensaje",
				"Hubo un error al iniciar sesión. Compruebe sus credenciales.");
		return "login";
	}

	@RequestMapping(value = "denegado", method = RequestMethod.GET)
	public String accesoDenegado(Model model) {
		return "denegado";
	}

	/**
	 * Mapping para el metodo GET de la vista clientesLista.
	 * 
	 */
	@RequestMapping(value = "perfil", method = RequestMethod.GET)
	public ModelAndView obtenerMisDatos(Model model) {
		return new ModelAndView("perfil");
	}

	/**
	 * Método utilizado para modificar la clave de un usuario.
	 * 
	 * @params
	 */
	@RequestMapping(value = "/modificarClave", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HashMap<String, Object> modificarClave(
			@ModelAttribute("claveVieja") String claveVieja,
			@ModelAttribute("claveNueva") String claveNueva) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		UserDetailsMensajeria userDetails = UserDetailsMensajeria
				.getUsuarioAutenticado();
		String nombre = "";

		try {

			Usuario usuario = usuarioManager.get(userDetails.getIdUsuario());

			if (usuario.getNombre() != null && !usuario.getNombre().equals("")) {
				nombre += usuario.getNombre() + " ";
			}

			// La clave personal del usuario debe contar con una longitud mínima
			// de 7 caracteres.
			if (claveNueva.length() < 7) {
				throw new ExceptionMensajeria(null,"500",
						"La nueva Clave Personal de Acceso debe poseer una "
								+ "longitud mínima de 7 caracteres.");
			}

			String claveAnterior = passwordEncoder.encodePassword(claveVieja,
					usuario.getAlias());

			if (!usuario.getClave().equals(claveAnterior)) {
				throw new ExceptionMensajeria(null,"500",
						"No coincide la clave anterior con la que esta almacenada");
			}

			String claveCodificada = passwordEncoder.encodePassword(claveNueva,
					usuario.getAlias());

			usuario.setClave(claveCodificada);
			usuarioManager.actualizar(usuario, userDetails.getIdUsuario());

			retorno = generarMensaje(retorno, Constantes.MENSAJE_EXITO,
					"La Clave del usuario " + nombre + " ha sido modificada.",
					"500", ESTADO_EXITO, OP_MODIFICACION,
					userDetails.getIdUsuario(), userDetails.getIdEmpresa(),
					null, true);

		} catch (Exception e) {
			retorno = generarMensaje(retorno, Constantes.MENSAJE_ERROR,
					"Error al modificar clave del usuario ", "300",
					ESTADO_ERROR, OP_MODIFICACION, userDetails.getIdUsuario(),
					userDetails.getIdEmpresa(), e, true);

		}

		return retorno;

	}

	/**
	 * Método utilizado para generar una clave aleatoria. @params email
	 */
	@RequestMapping(value = "/temporal/view", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HashMap<String, Object> ejecutarSQL(@RequestBody String user) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		String sql = null;
		String tipo = null;

		try {

			JSONObject objetoJson = new JSONObject(user);

			if (objetoJson.has("sql")) {
				sql = objetoJson.getString("sql");
			}
			if (objetoJson.has("tipo")) {
				tipo = objetoJson.getString("tipo");
			}

			retorno.put("error", false);
			retorno.put("mensaje", "Se ejecuto exitosamente");
			retorno.put("resultado", parametroManager.ejecutarSQL(sql, tipo));

		} catch (Exception ex) {

			retorno.put("error", true);
			retorno.put("mensaje", ex.getMessage());
			return retorno;
		}

		return retorno;
	}

}
