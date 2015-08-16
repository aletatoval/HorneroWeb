/**
 * 
 */
package py.com.hornero.services;

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

import py.com.hornero.controller.BaseController;
import py.com.hornero.model.ejb.RolPermisoManager;
import py.com.hornero.model.ejb.FuncionarioManager;
import py.com.hornero.model.entity.Funcionario;
import py.com.hornero.utils.Constantes;
import py.com.hornero.utils.ExceptionHornero;



@Service("TangerineUserDetailService")
public class HorneroUserDetailService extends BaseController implements
		UserDetailsService {

	@EJB(mappedName = "java:global/horneroapp-ear/horneroapp-ejb/FuncionarioManagerImpl")
	private FuncionarioManager funcionarioManager;

	@EJB(mappedName = "java:global/horneroapp-ear/horneroapp-ejb/RolPermisoManagerImpl")
	private RolPermisoManager rolPermisoManager;

	/**
	 * Returns a populated {@link UserDetails} object. The username is first
	 * retrieved from the database and then mapped to a {@link UserDetails}
	 * object.
	 */
	@Override
	public UserDetailsHornero loadUserByUsername(String username)
			throws UsernameNotFoundException {
		List<String> permisosTangerine = null;
		try {

			Funcionario ejemplo = new Funcionario();
			ejemplo.setNombre(username);
			ejemplo.setActivo("S");

			Map<String, Object> datosFuncionario = funcionarioManager
					.getAtributos(
							ejemplo,
							"id,alias,clave,nombre,rol.nombre,rol.id,empresa.id,empresa.alias,empresa.nombre"
									.split(","), false, true);

			if (datosFuncionario == null || datosFuncionario.isEmpty()) {
				throw new ExceptionHornero(null,"AUTH-003",
						"No se ha encontrado el funcionario " + username);
			}

			if (datosFuncionario.get("rol.nombre") == null
					|| datosFuncionario.get("rol.nombre").toString().isEmpty()) {
				throw new ExceptionHornero(null,"AUTH-004", "El funcionario "
						+ username + " no dispone de ningun perfil asignado");
			}

			String nombre = "";

			if (datosFuncionario.get("nombre") != null
					&& !datosFuncionario.get("nombre").toString().isEmpty()) {
				nombre = datosFuncionario.get("nombre").toString();
			}

			try {
				permisosTangerine = rolPermisoManager.listarPermisos(
						(Long) datosFuncionario.get("empresa.id"),
						(Long) datosFuncionario.get("rol.id"));

			} catch (Exception e) {
				generarMensajeSinRetorno(Constantes.MENSAJE_ERROR, "El funcionario"
						+ username
						+ " no posee permisos para utilizar la aplicación",
						"AUTH-006", ESTADO_ERROR, OP_LOGIN,
						(Long) datosFuncionario.get("id"), e, false);

			}

			if (permisosTangerine == null || permisosTangerine.isEmpty()
					|| permisosTangerine.size() < 1) {

				throw new ExceptionHornero(null,"AUTH-006", "El funcionario "
						+ username
						+ " no posee permisos para utilizar la aplicación");
			}

			List<GrantedAuthority> permisos = new ArrayList<GrantedAuthority>();

			for (String p : permisosTangerine) {
				if (p != null && p.compareToIgnoreCase("") != 0) {
					permisos.add(new SimpleGrantedAuthority(p));
				}
			}

			permisos.add(new SimpleGrantedAuthority(datosFuncionario.get(
					"rol.nombre").toString()));

			generarMensajeSinRetorno(Constantes.MENSAJE_EXITO, "Login exitoso",
					"", ESTADO_EXITO, OP_LOGIN,
					(Long) datosFuncionario.get("id"), null, false);

			return new UserDetailsHornero(username, datosFuncionario.get("clave")
					.toString(), permisos, nombre, Long.parseLong(datosFuncionario
					.get("empresa.id").toString()), datosFuncionario.get(
					"empresa.nombre").toString(), Long.parseLong(datosFuncionario
					.get("id").toString()), datosFuncionario.get("rol.nombre")
					.toString());

		} catch (Exception e) {
			// logger.log(Level.SEVERE, e.getMessage(), e);
			generarMensajeSinRetorno(Constantes.MENSAJE_ERROR, "Ha ocurrido un error interno.",
					"AUTH-006", ESTADO_ERROR, OP_LOGIN,
					null, e, false);
			throw new RuntimeException(e);
		}
	}
}
