/**
 * 
 */
package py.com.mensajeria.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import py.com.mensajeria.controller.BaseController;
import py.com.mensajeria.model.ejb.RolPermisoManager;
import py.com.mensajeria.model.ejb.UsuarioManager;
import py.com.mensajeria.model.entity.Usuario;
import py.com.mensajeria.utils.Constantes;
import py.com.mensajeria.utils.ExceptionMensajeria;

/**
 * @author Hermann Bottger
 * 
 */

@Service("TangerineUserDetailService")
public class MensajeriaUserDetailService extends BaseController implements
		UserDetailsService {

	@EJB(mappedName = "java:global/mensajeriaapp-ear/mensajeriaapp-ejb/UsuarioManagerImpl")
	private UsuarioManager usuarioManager;

	@EJB(mappedName = "java:global/mensajeriaapp-ear/mensajeriaapp-ejb/RolPermisoManagerImpl")
	private RolPermisoManager rolPermisoManager;

	/**
	 * Returns a populated {@link UserDetails} object. The username is first
	 * retrieved from the database and then mapped to a {@link UserDetails}
	 * object.
	 */
	@Override
	public UserDetailsMensajeria loadUserByUsername(String username)
			throws UsernameNotFoundException {
		List<String> permisosTangerine = null;
		try {

			Usuario ejemplo = new Usuario();
			ejemplo.setAlias(username);
			ejemplo.setActivo("S");

			Map<String, Object> datosUsuario = usuarioManager
					.getAtributos(
							ejemplo,
							"id,alias,clave,nombre,rol.nombre,rol.id,empresa.id,empresa.alias,empresa.nombre"
									.split(","), false, true);

			if (datosUsuario == null || datosUsuario.isEmpty()) {
				throw new ExceptionMensajeria(null,"AUTH-003",
						"No se ha encontrado el usuario " + username);
			}

			if (datosUsuario.get("rol.nombre") == null
					|| datosUsuario.get("rol.nombre").toString().isEmpty()) {
				throw new ExceptionMensajeria(null,"AUTH-004", "El usuario "
						+ username + " no dispone de ningun perfil asignado");
			}

			String nombre = "";

			if (datosUsuario.get("nombre") != null
					&& !datosUsuario.get("nombre").toString().isEmpty()) {
				nombre = datosUsuario.get("nombre").toString();
			}

			try {
				permisosTangerine = rolPermisoManager.listarPermisos(
						(Long) datosUsuario.get("empresa.id"),
						(Long) datosUsuario.get("rol.id"));

			} catch (Exception e) {
				generarMensajeSinRetorno(Constantes.MENSAJE_ERROR, "El usuario"
						+ username
						+ " no posee permisos para utilizar la aplicación",
						"AUTH-006", ESTADO_ERROR, OP_LOGIN,
						(Long) datosUsuario.get("id"),
						(Long) datosUsuario.get("empresa.id"), e, false);

			}

			if (permisosTangerine == null || permisosTangerine.isEmpty()
					|| permisosTangerine.size() < 1) {

				throw new ExceptionMensajeria(null,"AUTH-006", "El usuario "
						+ username
						+ " no posee permisos para utilizar la aplicación");
			}

			List<GrantedAuthority> permisos = new ArrayList<GrantedAuthority>();

			for (String p : permisosTangerine) {
				if (p != null && p.compareToIgnoreCase("") != 0) {
					permisos.add(new SimpleGrantedAuthority(p));
				}
			}

			permisos.add(new SimpleGrantedAuthority(datosUsuario.get(
					"rol.nombre").toString()));

			generarMensajeSinRetorno(Constantes.MENSAJE_EXITO, "Login exitoso",
					"", ESTADO_EXITO, OP_LOGIN,
					(Long) datosUsuario.get("id"),
					(Long) datosUsuario.get("empresa.id"), null, false);

			return new UserDetailsMensajeria(username, datosUsuario.get("clave")
					.toString(), permisos, nombre, Long.parseLong(datosUsuario
					.get("empresa.id").toString()), datosUsuario.get(
					"empresa.nombre").toString(), Long.parseLong(datosUsuario
					.get("id").toString()), datosUsuario.get("rol.nombre")
					.toString());

		} catch (Exception e) {
			// logger.log(Level.SEVERE, e.getMessage(), e);
			generarMensajeSinRetorno(Constantes.MENSAJE_ERROR, "Ha ocurrido un error interno.",
					"AUTH-006", ESTADO_ERROR, OP_LOGIN,
					null,null, e, false);
			throw new RuntimeException(e);
		}
	}
}
