/**
 * 
 */
package py.com.hornero.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import py.com.hornero.model.ejb.LogAppManager;
import py.com.hornero.model.ejb.ParametroManager;
import py.com.hornero.model.ejb.UsuarioManager;
import py.com.hornero.model.entity.Empresa;
import py.com.hornero.model.entity.LogApp;
import py.com.hornero.model.entity.Usuario;
import py.com.hornero.utils.Constantes;
import py.com.hornero.utils.ExceptionHornero;

/**
 * Controller base para todos los controller. Aquí se disponen los métodos que
 * sean comunes a todos
 * 
 * @author Osmar Olmedo
 * 
 */
@Controller
public class BaseController {

	public static final String ESTADO_EXITO = "Aprobado por Tangerine";
	public static final String ESTADO_ERROR = "Rechazado por Tangerine";
	public static final String OP_ALTA = "Alta";
	public static final String OP_MODIFICACION = "Modificacion";
	public static final String OP_BAJA = "Baja";
	public static final String OP_DESACTIVACION = "Desactivacion";
	public static final String OP_ACTIVACION = "Activacion";
	public static final String OP_VISUALIZACION = "Visualizacion";
	public static final String OP_ASOCIACION = "Asociacion";
	public static final String OP_DESASOCIACION = "Desasociacion";
	public static final String OP_IMPORTACION = "Importacion";
	public static final String OP_EXPORTACION = "Exportacion";
	public static final String OP_VALIDACION = "Validacion";
	public static final String OP_VERSION = "Version";

	public static final String OP_LOGIN = "Login";
	public static final String OP_SINCRONIZACION = "Sincronizacion";
	public static final String OP_DESCARGA = "Descarga";

	@EJB(mappedName = "java:global/mensajeriaapp-ear/mensajeriaapp-ejb/UsuarioManagerImpl")
	public UsuarioManager usuarioManager;

	@EJB(mappedName = "java:global/mensajeriaapp-ear/mensajeriaapp-ejb/ParametroManagerImpl")
	public ParametroManager parametroManager;

	@EJB(mappedName = "java:global/mensajeriaapp-ear/mensajeriaapp-ejb/LogAppManagerImpl")
	public LogAppManager logAppManager;

	public SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSSSSS");
	public SimpleDateFormat sdfSimple = new SimpleDateFormat("dd-MM-yyyy");
	public SimpleDateFormat formatInput = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public SimpleDateFormat formatoSimple = new SimpleDateFormat("dd/MM/yyyy");
	public SimpleDateFormat formatoAmericanoSimple = new SimpleDateFormat(
			"yyyy-MM-dd");
	public SimpleDateFormat formatoHoraMin = new SimpleDateFormat("HH:mm");
	public SimpleDateFormat formatoFechaHora = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm");
	public SimpleDateFormat formatoCompleto = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");
	public SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");

	public static final Logger logger = LoggerFactory.getLogger("tangerineapp");

	public String convertidorString(HSSFCell cell) {
		if (cell == null) {
			return null;
		}
		if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			return String.valueOf((int) cell.getNumericCellValue());
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			return cell.getStringCellValue();
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_ERROR) {
			return "";// cell.getErrorCellValue();
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
			try {
				return cell.getStringCellValue();
			} catch (Exception e) {
				return "";
			}
		} else {
			return cell.getStringCellValue();
		}
	}

	public String formartearPresentacion(String palabra) {
		String retorno = "";
		String[] palabras = palabra.split(" ");
		for (int i = 0; i < palabras.length; i++) {
			String temp = palabra.substring(0, 1).toUpperCase();
			temp += palabra.substring(1).toLowerCase();
			if (i > 0) {
				retorno += " ";
			}
			retorno += temp;
		}
		return retorno;
	}

	/**
	 * @param map
	 * 
	 */
	public void formatearFecha(Map<String, Object> map) {
		String fecha;
		if (map.containsKey("fechaCreacion")
				&& map.get("fechaCreacion") != null) {
			fecha = sdf.format(map.get("fechaCreacion"));
			map.put("fechaCreacion", fecha);
		}
		if (map.containsKey("fechaModificacion")
				&& map.get("fechaModificacion") != null) {
			fecha = sdf.format(map.get("fechaModificacion"));
			map.put("fechaModificacion", fecha);
		}
		if (map.containsKey("fechaEliminacion")
				&& map.get("fechaEliminacion") != null) {
			fecha = sdf.format(map.get("fechaEliminacion"));
			map.put("fechaEliminacion", fecha);
		}
	}

	public List<String> inicializarFechas(String stringInicio, String stringFin) {
		List<String> fechas = new ArrayList<String>();
		if (stringInicio == null || stringInicio.isEmpty()) {
			Calendar date = Calendar.getInstance();
			date.set(Calendar.DAY_OF_MONTH, 1);
			stringInicio = formatoSimple.format(date.getTime());

		}

		if (stringFin == null || stringFin.isEmpty()) {
			Calendar date = Calendar.getInstance();
			date.set(Calendar.DATE, date.getActualMaximum(Calendar.DATE));
			stringFin = formatoSimple.format(date.getTime());

		}

		fechas.add(stringInicio);
		fechas.add(stringFin);

		return fechas;

	}

	public HashMap<String, Object> generarMensaje(
			HashMap<String, Object> retorno, String tipoMensaje,
			String mensaje, String codigoError, String estado,
			String operacion, Long idUsuario, Long idEmpresa, Exception ex,
			boolean almacenar) {

		if (retorno == null) {
			retorno = new HashMap<String, Object>();
		}

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

			if (ex != null && ex instanceof ExceptionHornero
					&& estado.compareToIgnoreCase(ESTADO_ERROR) == 0) {
				retorno.put("error", true);
				mensaje = ex.getMessage();
				ExceptionHornero e = (ExceptionHornero) ex;
				
				log.setCodigoError(e.getCodigo());
				retorno.put("codigoError", e.getCodigo());
				String campo = e.getCampo();
				
				if(campo != null){
					retorno.put(campo, e.getMessage());
				}else{
					retorno.put("campo", e.getMessage());
				}

				if (e.getEstado() != null) {
					log.setEstado(e.getEstado());
				} else {
					log.setEstado(ESTADO_ERROR);
				}
				logger.error(mensaje, ex);

			} else if (ex != null
					&& estado.compareToIgnoreCase(ESTADO_ERROR) == 0) {
				retorno.put("error", true);

				log.setCodigoError(codigoError);
				log.setEstado(estado);
				logger.error(mensaje, ex);

			} else {
				retorno.put("error", false);

				log.setEstado(estado);
				logger.info(mensaje);
			}

			retorno.put("mensaje", mensaje);

			retorno.put("tipoMensaje", tipoMensaje);

			log.setMensaje(mensaje);

			if (almacenar) {
				logAppManager.save(log);
			}

		} catch (Exception e) {
			retorno.put("error", true);

			if (retorno.get("mensaje") != null) {
				retorno.put("mensaje", "Error al generar el retorno");
			}
			retorno.put("tipoMensaje", Constantes.MENSAJE_ERROR);
			logger.error("Error al generar el registro de error", e);
		}

		return retorno;
	}

	/*
	 * Metodo generador de mensajes para el log.
	 */
	public void generarMensajeSinRetorno(String tipoMensaje, String mensaje,
			String codigoError, String estado, String operacion,
			Long idUsuario, Long idEmpresa, Exception ex, boolean almacenar) {

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

			if (ex != null && ex instanceof ExceptionHornero
					&& estado.compareToIgnoreCase(ESTADO_ERROR) == 0) {

				mensaje = ex.getMessage();
				ExceptionHornero e = (ExceptionHornero) ex;
				log.setCodigoError(e.getCodigo());

				if (e.getEstado() != null) {
					log.setEstado(e.getEstado());
				} else {
					log.setEstado(ESTADO_ERROR);
				}
				logger.error(mensaje, ex);

			} else if (ex != null
					&& estado.compareToIgnoreCase(ESTADO_ERROR) == 0) {

				log.setCodigoError(codigoError);
				log.setEstado(estado);
				logger.error(mensaje, ex);

			} else {
				log.setEstado(estado);
				logger.info(mensaje);
			}

			log.setMensaje(mensaje);

			if (almacenar) {
				logAppManager.save(log);
			}

		} catch (Exception e) {
			logger.error("Error al generar el registro de error", e);
		}
	}

	public List<String> manejoMensajeriaSinLog(String codigo, String estado,
			String mensaje, Exception ex) {

		List<String> respuesta = new ArrayList<String>();

		if (ex != null && ex instanceof ExceptionHornero) {
			ExceptionHornero e = (ExceptionHornero) ex;
			mensaje = e.getMessage();
			respuesta.add(mensaje);
			respuesta.add(e.getCodigo());
			respuesta.add(e.getEstado());
		} else {
			respuesta.add(mensaje);
			respuesta.add(codigo);
			respuesta.add(estado);
		}

		return respuesta;
	}
}
