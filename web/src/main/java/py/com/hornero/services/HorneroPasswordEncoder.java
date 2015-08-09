package py.com.hornero.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import py.com.hornero.controller.BaseController;
import py.com.hornero.model.ejb.LogAppManager;
import py.com.hornero.model.ejb.UsuarioManager;
import py.com.hornero.model.entity.Empresa;
import py.com.hornero.model.entity.LogApp;
import py.com.hornero.model.entity.Usuario;
import py.com.hornero.utils.ExceptionHornero;

public class HorneroPasswordEncoder extends Md5PasswordEncoder {

	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger("mensajeriaapp");

	private UsuarioManager usuarioManager;

	private LogAppManager logAppManager;

	private Context context;

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt)
			throws BadCredentialsException {

		String pass2 = encodePassword(rawPass, salt);
		Usuario usuario = null;

		try {

			Usuario ejemplo = new Usuario();
			ejemplo.setAlias(salt.toString());
			ejemplo.setClave(pass2);

			inicializarUsuarioManager();

			usuario = usuarioManager.get(ejemplo);

			if (usuario == null) {
				throw new BadCredentialsException(manejoMensajes(
						"AUTH-001",
						BaseController.ESTADO_ERROR,
						BaseController.OP_LOGIN,
						"No se pudo identificar a la línea " + salt.toString()
								+ ", favor verificar la información ingresada",
						null, null, null).get(0));
			}

			manejoMensajes("", BaseController.ESTADO_EXITO,
					BaseController.OP_LOGIN, "Login exitoso", usuario.getId(),
					usuario.getEmpresa().getId(), null);
			
			return true;

		} catch (Exception ex) {
			throw new BadCredentialsException(ex.getMessage());
		}

	}

	public List<String> manejoMensajes(String codigoError, String estado,
			String operacion, String mensaje, Long idUsuario, Long idEmpresa,
			Exception ex) {

		List<String> respuesta = new ArrayList<String>();
		inicializarLogAppManager();
		try {
			LogApp log = new LogApp();

			log.setOperacion(operacion);
			log.setFechaAccion(new Timestamp(System.currentTimeMillis()));

			if (idUsuario != null && idUsuario > 0) {
				log.setUsuario(new Usuario(idUsuario));
			}
			if (idEmpresa != null && idEmpresa > 0) {
				log.setEmpresa(new Empresa(idEmpresa));
			}

			if (ex != null
					&& ex instanceof ExceptionHornero
					&& estado.compareToIgnoreCase(BaseController.ESTADO_ERROR) == 0) {
				mensaje = ex.getMessage();
				ExceptionHornero e = (ExceptionHornero) ex;
				log.setCodigoError(e.getCodigo());

				if (e.getEstado() != null) {
					log.setEstado(e.getEstado());
				} else {
					log.setEstado(BaseController.ESTADO_ERROR);
				}
				logger.error(mensaje, ex);

			} else if (ex != null
					&& estado.compareToIgnoreCase(BaseController.ESTADO_ERROR) == 0) {
				log.setCodigoError(codigoError);
				log.setEstado(estado);
				logger.error(mensaje, ex);

			} else {
				log.setEstado(estado);
				logger.info(mensaje);
			}

			log.setMensaje(mensaje);

			respuesta.add(log.getMensaje());
			respuesta.add(log.getCodigoError() + "");
			respuesta.add(log.getEstado());

			logAppManager.save(log);

		} catch (Exception e) {

			if (respuesta.size() > 0) {
				if (respuesta.get(0) == null) {
					respuesta.set(0, mensaje);
				}
				if (respuesta.get(1) == null) {
					respuesta.set(1, "-1");
				}

			} else {
				respuesta.add(mensaje);
				respuesta.add("-1");
			}

			logger.error("Error al generar el registro de error", e);
		}

		return respuesta;
	}

	private void inicializarUsuarioManager() {
		if (context == null)
			try {
				context = new InitialContext();
			} catch (NamingException e1) {
				throw new RuntimeException(
						"No se puede inicializar el contexto", e1);
			}
		if (usuarioManager == null) {
			try {

				usuarioManager = (UsuarioManager) context
						.lookup("java:app/mensajeriaapp-ejb/UsuarioManagerImpl");
			} catch (NamingException ne) {
				throw new RuntimeException(
						"No se encuentra EJB valor Manager: ", ne);
			}
		}
	}

	private void inicializarLogAppManager() {
		if (context == null)
			try {
				context = new InitialContext();
			} catch (NamingException e1) {
				throw new RuntimeException(
						"No se puede inicializar el contexto", e1);
			}
		if (logAppManager == null) {
			try {

				logAppManager = (LogAppManager) context
						.lookup("java:app/mensajeriaapp-ejb/LogAppManagerImpl");
			} catch (NamingException ne) {
				throw new RuntimeException(
						"No se encuentra EJB valor Manager: ", ne);
			}
		}
	}

}
