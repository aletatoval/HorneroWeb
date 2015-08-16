package py.com.hornero.controller;

/**
 * 
 * @author Christian Jara, Osmar Olmedo
 */
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import py.com.hornero.model.ejb.LocalizacionManager;
import py.com.hornero.model.entity.Localizacion;
import py.com.hornero.model.entity.Funcionario;
import py.com.hornero.services.UserDetailsHornero;
import py.com.hornero.utils.Constantes;
import py.com.hornero.utils.ExceptionHornero;

@Controller
@RequestMapping(value = "/localizaciones")
public class LocalizacionController extends BaseController {

	@EJB(mappedName = "java:global/horneroapp-ear/horneroapp-ejb/LocalizacionManagerImpl")
	private LocalizacionManager localizacionManager;

	/**
	 * Mapping para el metodo GET de la vista localizaciones.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView vista() {
		return new ModelAndView("localizacion");
	}

	/**
	 * Servicio REST para obtener última ubicación
	 * 
	 */
	@RequestMapping(value = "/ubicacion", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	HashMap<String, Object> getUltimaPosicion(
			@ModelAttribute("id") Integer idUsuario) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		HashMap<String, Object> meta = new HashMap<String, Object>();
		UserDetailsHornero userDetails = UserDetailsHornero
				.getFuncionarioAutenticado();

		Map<String, Object> funcionarioMap = funcionarioManager.getAtributos(
				new Funcionario(Long.parseLong(idUsuario.toString())),
				"nombre,imei".split(","));

		String nombreFuncionario = (String) funcionarioMap.get("nombre");

		try {

			List<Map<String, Object>> resultados = null;
			/**
			 * Obtenemos el imei y lo utilizamos para la creación del ejemplo de
			 * localización.
			 */
			Localizacion ejemploLoca = new Localizacion();

			ejemploLoca.setFuncionario(new Funcionario(Long.parseLong(idUsuario
					.toString())));

			ejemploLoca.setImei((String) funcionarioMap.get("imei"));

			resultados = localizacionManager.listUltimaLocalizacion(
					ejemploLoca,
					"id,fechaCreacion,fecha,hora,imei,latitud,longitud,precision"
							.split(","));

			if (resultados != null && resultados.size() > 0
					&& resultados.get(0) != null
					&& resultados.get(0).containsKey("precision")
					&& resultados.get(0).get("precision") != null) {

				Map<String, Object> map = resultados.get(0);

				map.put("nombreFuncionario", nombreFuncionario);

				Integer presicion = (new Double(resultados.get(0)
						.get("precision").toString())).intValue();
				resultados.get(0).put("precision", presicion);

				String fecha = resultados.get(0).get("fechaCreacion")
						.toString();
				String fechaMostrar = sdfSimple.format(sdf.parse(fecha));
				resultados.get(0).put("fechaMostrar", fechaMostrar);

				String horaMostrar = sdfHora.format(sdf.parse(fecha));
				resultados.get(0).put("horaMostrar", horaMostrar);

			} else {
				throw new ExceptionHornero(null,"600", "El Usuario "
						+ nombreFuncionario
						+ " no posee marcaciones en el día especificado.");
			}

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"Se obtuvo exitosamente la ubicación", "600", ESTADO_EXITO,
					OP_VISUALIZACION, userDetails.getIdFuncionario(),
					 null, false);

			retorno.put("ubicacion", resultados.get(0));
			retorno.put("meta", meta);

		} catch (Exception e) {
			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al obtener ubicación del funcionario", "600",
					ESTADO_ERROR, OP_VISUALIZACION, userDetails.getIdFuncionario(),
					 e, false);
			retorno.put("meta", meta);
		}

		return retorno;

	}

	/**
	 * Servicio REST para obtener última ruta del dia
	 * 
	 */
	@RequestMapping(value = "/rutadeldia", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	HashMap<String, Object> getRutaDelDia(
			@ModelAttribute("id") Integer idUsuario,
			@ModelAttribute("fechaRuta") String fechaRuta,
			@ModelAttribute("inicioRuta") String inicioRuta,
			@ModelAttribute("finalRuta") String finalRuta,
			@ModelAttribute("distanciaPuntos") String distanciaPuntos) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		HashMap<String, Object> meta = new HashMap<String, Object>();
		// ListaDTO<Map<String, Object>> lista = new ListaDTO<Map<String,
		// Object>>();
		UserDetailsHornero userDetails = UserDetailsHornero
				.getFuncionarioAutenticado();

		Funcionario funcionario = funcionarioManager.get(Long.parseLong(idUsuario
				.toString()));
		String nombreFuncionario = "";
		if (funcionario.getNombre() != null) {
			nombreFuncionario = funcionario.getNombre();

		} else {
			nombreFuncionario = funcionario.getNombre();
		}

		try {

			Double distanciaEntrePuntos;
			if (distanciaPuntos.isEmpty()) {
				distanciaEntrePuntos = new Double("0");
			} else {
				distanciaEntrePuntos = new Double(distanciaPuntos.toString());

			}

			String fecha[] = fechaRuta.split("/");
			int anho = Integer.parseInt(fecha[2]); // Año
			int mes = Integer.parseInt(fecha[1]); // Mes
			int dia = Integer.parseInt(fecha[0]); // Día

			String horaini[] = inicioRuta.split(":");
			int hhini = Integer.parseInt(horaini[0]);
			int mmini = Integer.parseInt(horaini[1]);
			int ssini = Integer.parseInt(horaini[2]);

			String horafin[] = finalRuta.split(":");
			int hhfin = Integer.parseInt(horafin[0]);
			int mmfin = Integer.parseInt(horafin[1]);
			int ssfin = Integer.parseInt(horafin[2]);

			if (hhfin < hhini) {
				throw new ExceptionHornero(null,"600",
						"El final del intervalo no puede ser menor que el inicio.");
			} else if (hhfin == hhini && mmfin <= mmini && ssfin <= ssini) {
				throw new ExceptionHornero(null,"600",
						"El final del intervalo no puede ser menor o igual que el inicio.");
			}

			/**
			 * Obtenemos el email y lo utilizamos para la creación del ejemplo
			 * de localización.
			 */
			/**
			 * [2012-10-24] dbrassel La ruta del día a desplegar del usuario en
			 * cuestión corresponde a aquella cuyo imei sea el indicado en la
			 * Entidad Usuario.
			 */

			Localizacion ejemplo = new Localizacion();
			ejemplo.setFuncionario(funcionario);

			List<Map<String, Object>> resultados = null;
			List<Map<String, Object>> resultadosEnvio = null;

			// Seteamos el intervalo de inicio de la información de coordenadas
			// de la ruta.
			Calendar hoy = Calendar.getInstance();
			hoy.set(Calendar.YEAR, anho);
			hoy.set(Calendar.MONTH, mes - 1); // Al valor del mes hay que
			// restarle (1)
			hoy.set(Calendar.DAY_OF_MONTH, dia);

			hoy.set(Calendar.HOUR_OF_DAY, hhini);
			hoy.set(Calendar.MINUTE, mmini);
			hoy.set(Calendar.SECOND, ssini);

			// Seteamos el intervalo de fin de la información de coordenadas de
			// la ruta.

			Timestamp fechaInicio = new Timestamp(hoy.getTimeInMillis());// Timestamp.valueOf(sdf.format(new
			// Date()));
			hoy.set(Calendar.HOUR_OF_DAY, hhfin);
			hoy.set(Calendar.MINUTE, mmfin);
			hoy.set(Calendar.SECOND, ssfin);

			Timestamp fechaFin = new Timestamp(hoy.getTimeInMillis());

			List<Object> may = new ArrayList<Object>();
			List<Object> men = new ArrayList<Object>();
			List<String> attrmay = new ArrayList<String>();
			List<String> attrmen = new ArrayList<String>();

			may.add(fechaInicio);
			men.add(fechaFin);
			attrmay.add("fechaCreacion");
			attrmen.add("fechaCreacion");

			resultados = localizacionManager.listAtributos(ejemplo,
					"id,fechaCreacion,imei,latitud,longitud,precision"
							.split(","), "fechaCreacion,id".split(","),
					"ASC,ASC".split(","), false, false, attrmay, may, attrmen,
					men);

			if (resultados != null && !resultados.isEmpty()) {
				resultadosEnvio = new ArrayList<Map<String, Object>>();

				for (int i = 0; i < resultados.size(); i++) {
					Map<String, Object> map = resultados.get(i);

					String fechaTracking = map.get("fechaCreacion").toString();
					String horaMostrar = sdfHora.format(sdf
							.parse(fechaTracking));
					// String hora = map.get("hora").toString();
					map.put("hora", horaMostrar);
					map.put("pkUsuario", funcionario.getId());
					map.put("nombreUsuario", nombreFuncionario);
					map.put("fechaMostrar",
							sdfSimple.format(sdf.parse(fechaTracking)));

					if (i != 0 && i != (resultados.size() - 1)) {

						Double latitud1 = new Double(map.get("latitud")
								.toString());
						Double longitud1 = new Double(map.get("longitud")
								.toString());

						Map<String, Object> map2 = resultados.get(i + 1);
						Double latitud2 = new Double(map2.get("latitud")
								.toString());
						Double longitud2 = new Double(map2.get("longitud")
								.toString());

						if (latitud1 != null && longitud1 != null
								&& latitud2 != null && longitud2 != null) {
							Double distanciaLatitud = (latitud1 - latitud2);
							Double distanciaLongitud = (longitud1 - longitud2);

							Double distanciaCalculada = (Math
									.sqrt((distanciaLatitud * distanciaLatitud)
											+ (distanciaLongitud * distanciaLongitud))) * 100000;
							if (distanciaEntrePuntos <= distanciaCalculada) {
								resultadosEnvio.add(map);
							}
						} else {
							resultadosEnvio.add(map);
						}
					} else {
						resultadosEnvio.add(map);

					}
				}
			} else {
				throw new ExceptionHornero(null,"600", "El funcionario "
						+ nombreFuncionario
						+ " no posee información de ruta del día.");
			}

			if (resultadosEnvio != null) {
				meta.put("TotalDatos", resultadosEnvio.size());
			}

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"Se listaron exitosamentes las localizaciones", "600",
					ESTADO_EXITO, OP_VISUALIZACION, userDetails.getIdFuncionario(),
					 null, false);

			retorno.put("meta", meta);
			retorno.put("ruta", resultadosEnvio);

		} catch (Exception e) {
			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al obtener ubicación tracking del dia del usuario",
					"600", ESTADO_ERROR, OP_VISUALIZACION,
					userDetails.getIdFuncionario(),  e,
					false);
			retorno.put("meta", meta);

		}

		return retorno;

	}
}
