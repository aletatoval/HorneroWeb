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
import py.com.hornero.model.ejb.FuncionarioManager;
import py.com.hornero.model.entity.Empresa;
import py.com.hornero.model.entity.LogApp;
import py.com.hornero.model.entity.Funcionario;
import py.com.hornero.utils.ExceptionHornero;

public class HorneroPasswordEncoder extends Md5PasswordEncoder {

	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger("horneroapp");

	private FuncionarioManager funcionarioManager;

	private LogAppManager logAppManager;

	private Context context;

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt)
			throws BadCredentialsException {

		String pass2 = encodePassword(rawPass, salt);
		Funcionario funcionario = null;

		try {

			Funcionario ejemplo = new Funcionario();
			ejemplo.setNombre(salt.toString());
			ejemplo.setContraseña(pass2);

			inicializarFuncionarioManager();

			funcionario = funcionarioManager.get(ejemplo);

			if (funcionario == null) {
				throw new BadCredentialsException(manejoMensajes(
						"AUTH-001",
						BaseController.ESTADO_ERROR,
						BaseController.OP_LOGIN,
						"No se pudo identificar a la línea " + salt.toString()
								+ ", favor verificar la información ingresada",
						 null, null).get(0));
			}

			manejoMensajes("", BaseController.ESTADO_EXITO,
					BaseController.OP_LOGIN, "Login exitoso", funcionario.getId(),
					 null);
			
			return true;

		} catch (Exception ex) {
			throw new BadCredentialsException(ex.getMessage());
		}

	}

	public List<String> manejoMensajes(String codigoError, String estado,
			String operacion, String mensaje, Long idFuncionario,
			Exception ex) {

		List<String> respuesta = new ArrayList<String>();
		inicializarLogAppManager();
		try {
			LogApp log = new LogApp();

			log.setOperacion(operacion);
			log.setFechaAccion(new Timestamp(System.currentTimeMillis()));

			if (idFuncionario != null && idFuncionario > 0) {
				log.setFuncionario(new Funcionario(idFuncionario));
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

	private void inicializarFuncionarioManager() {
		if (context == null)
			try {
				context = new InitialContext();
			} catch (NamingException e1) {
				throw new RuntimeException(
						"No se puede inicializar el contexto", e1);
			}
		if (funcionarioManager == null) {
			try {

				funcionarioManager = (FuncionarioManager) context
						.lookup("java:app/horneroapp-ejb/FuncionarioManagerImpl");
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
						.lookup("java:app/horneroapp-ejb/LogAppManagerImpl");
			} catch (NamingException ne) {
				throw new RuntimeException(
						"No se encuentra EJB valor Manager: ", ne);
			}
		}
	}

}
