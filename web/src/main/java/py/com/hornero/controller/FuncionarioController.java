/**
 * 
 */
package py.com.hornero.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import py.com.hornero.model.entity.Empresa;
import py.com.hornero.model.entity.Funcionario;
import py.com.hornero.services.UserDetailsHornero;
import py.com.hornero.utils.Constantes;
import py.com.hornero.utils.ExceptionHornero;
import py.com.hornero.utils.utils.FiltroDTO;
import py.com.hornero.utils.utils.ReglaDTO;

/**
 * Controller para la entidad Funcionario. Implementación de mappings, servicios
 * REST y métodos de mapeo privados y públicos.
 * 
 */
@Controller
@RequestMapping(value = "/funcionario")
public class FuncionarioController extends BaseController {

	@Autowired
	PasswordEncoder passwordEncoder;

	private String atributosFuncionarios = "id,nombre,alias,clave,documento,telefono,email,imei,imsi,tiempoMuestra,horaInicioMuestra"
			+ ",horaFinMuestra,rol.nombre,empresa.id,empresa.nombre,activo";

	private String permisosDesactivados = "Funcionario.Activar,Funcionario.Desactivar";

	/**
	 * Mapping para el metodo GET de la vista funcionarioForm.
	 * 
	 */
	@RequestMapping(value = "/{id}/{accion}", method = RequestMethod.GET)
	public ModelAndView formulario(@PathVariable("id") Long id,
			@PathVariable("accion") String accion) {

		ModelAndView retorno = new ModelAndView("funcionarioForm");
		retorno.addObject("editar", accion.compareToIgnoreCase("editar") == 0);
		retorno.addObject("id", id);
		return retorno;
	}

	/**
	 * Servicio REST para listar todas las Funcionarios
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	HashMap<String, Object> lista(@ModelAttribute("_search") boolean filtrar,
			@ModelAttribute("filters") String filtros,
			@ModelAttribute("page") Integer pagina,
			@ModelAttribute("rows") Integer cantidad,
			@ModelAttribute("sidx") String ordenarPor,
			@ModelAttribute("sord") String sentidoOrdenamiento,
			@ModelAttribute("todos") boolean todos) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();

		HashMap<String, Object> meta = new HashMap<String, Object>();

		UserDetailsHornero userDetails = UserDetailsHornero
				.getFuncionarioAutenticado();

		Funcionario ejemplo = new Funcionario();
		try {

			Gson gson = new Gson();
			String camposFiltros = null;
			String valorFiltro = null;

			if (filtrar) {
				FiltroDTO filtro = gson.fromJson(filtros, FiltroDTO.class);
				if (filtro.getGroupOp().compareToIgnoreCase(Constantes.OP_OR) == 0) {
					for (ReglaDTO regla : filtro.getRules()) {
						if (camposFiltros == null) {
							camposFiltros = regla.getField();
							valorFiltro = regla.getData();
						} else {
							camposFiltros += "," + regla.getField();
						}
					}
				} else {
					ejemplo = generarEjemplo(filtro, ejemplo);
				}

			}

			ejemplo.setActivo(userDetails.getCampoActivo(permisosDesactivados));

			pagina = pagina != null ? pagina : 1;
			Integer total = 0;

			if (!todos) {
				total = funcionarioManager.total(ejemplo, true, true);
			}

			Integer inicio = ((pagina - 1) < 0 ? 0 : pagina - 1) * cantidad;

			if (total < inicio) {
				inicio = total - total % cantidad;
				pagina = total / cantidad;
			}

			List<Map<String, Object>> listMapFuncionarios = funcionarioManager
					.listAtributos(ejemplo, atributosFuncionarios.split(","), todos,
							inicio, cantidad, ordenarPor.split(","),
							sentidoOrdenamiento.split(","), true, true,
							camposFiltros, valorFiltro);
			if (todos) {
				total = listMapFuncionarios.size();
			}

			retorno.put("funcionario", listMapFuncionarios);

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"Se listaron exitosamentes los funcionario", "300",
					ESTADO_EXITO, OP_VISUALIZACION, userDetails.getIdFuncionario(),
					null, false);

			meta.put("totalDatos", total); //Puesto sólo para probar paginación, porque retornaba cero
			meta.put("pagina", pagina);

		} catch (Exception ex) {

			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al obtener la lista de empresas", "300",
					ESTADO_ERROR, OP_VISUALIZACION, userDetails.getIdFuncionario(),
					ex, false);

		}

		retorno.put("meta", meta);
		return retorno;
	}

	/**
	 * Servicio REST para obtener una funcionario por id
	 * 
	 * @param id
	 *            el id del funcionario a obtener
	 */

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	HashMap<String, Object> obtener(@PathVariable("id") Long id) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		HashMap<String, Object> meta = new HashMap<String, Object>();

		UserDetailsHornero userDetails = UserDetailsHornero
				.getFuncionarioAutenticado();

		try {

			Map<String, Object> funcionario = funcionarioManager.getAtributos(
					new Funcionario(id), atributosFuncionarios.split(","));

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"El funcionario se obtuvo exitosamente", "300", ESTADO_EXITO,
					OP_VISUALIZACION, userDetails.getIdFuncionario(),
					null, false);

			retorno.put("funcionario", funcionario);

		} catch (Exception ex) {

			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Error al obtener el funcionario solicitado", "300",
					ESTADO_ERROR, OP_VISUALIZACION, userDetails.getIdFuncionario(),
					 ex, false);

		}

		retorno.put("meta", meta);
		return retorno;

	}

	/**
	 * Mapping para el metodo POST agregar funcionario
	 * 
	 * @param funcionarioRecibido
	 *            la entidad funcionario a a persistir en la bd
	 */
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public HashMap<String, Object> agregar(@RequestBody Funcionario nuevo,  HttpServletResponse response) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		HashMap<String, Object> meta = new HashMap<String, Object>();

		UserDetailsHornero userDetails = UserDetailsHornero
				.getFuncionarioAutenticado();

		logger.info("Inició correctamente el método agregar funcionario por el funcionario "
				+ userDetails.getIdFuncionario());

		try {
			// La clave personal del funcionario debe contar con una longitud mínima
			// de 7 caracteres.
			if (nuevo.getContraseña().length() < 7) {
				throw new ExceptionHornero("contraseña","268",
						"La Clave personal contraseña debe poseer una "
								+ "longitud minima de 7 caracteres.");
			}

			// Controlamos que el documento del funcionario no está ya en uso
			Funcionario ejemploDocumento = new Funcionario();
			ejemploDocumento.setDocumento(nuevo.getDocumento());

			if (funcionarioManager.total(ejemploDocumento) > 0) {
				throw new ExceptionHornero("documento","270",
						"El documento del funcionario ya existe");
			}

			// Controlamos que el nombre del funcionario no sea nulo o vacío.
			if (nuevo.getNombre().length() == 0) {
				throw new ExceptionHornero("nombre","271",
						"El nombre del funcionario no debe ser vacio.");
			}

			// Controlamos que la telefono del funcionario no sea nulo o vacío.

			// Controlamos que la telefono del funcionario no está ya en uso
			Funcionario ejemploTelefono = new Funcionario();
			ejemploTelefono.setTelefono(nuevo.getTelefono());

			if (funcionarioManager.total(ejemploTelefono) > 0) {
				throw new ExceptionHornero("telefono","272",
						"La telefono del funcionario ya está en uso");
			}

			// La telefono del funcionario no debe poseer espacios en blanco
			if (nuevo.getTelefono().indexOf(" ") > 0) {
				throw new ExceptionHornero("linea","273",
						"la línea del funcionario no puede contener "
								+ "espacios en blanco.");
			}

			// Controlamos que la clave del funcionario no sea nulo o vacío
			if (nuevo.getContraseña() == null
					|| nuevo.getContraseña().compareToIgnoreCase("") == 0) {
				throw new ExceptionHornero("clave","274",
						"La clave del funcionario no puede estar vacía");
			}

			// Controlamos que la clave del Funcionario no está ya en uso
			Funcionario ejemploContraseña = new Funcionario();
			ejemploContraseña.setContraseña(nuevo.getContraseña());

			if (funcionarioManager.total(ejemploContraseña) > 0) {
				throw new ExceptionHornero("clave","275",
						"La clave del Funcionario ya está en uso");
			}

			/**
			 * A continuación se realizará un chequeo de que un Funcionario al
			 * agregar no exista aún en la base de datos
			 */
			Funcionario ejemplo = new Funcionario();
			ejemplo.setTelefono(nuevo.getTelefono());

			if (funcionarioManager.total(ejemplo) > 0) {
				throw new ExceptionHornero(null,"276",
						"El Funcionario ya se encuentra en el sistema.");
			}

			nuevo.setTelefono(nuevo.getTelefono());

			// se codifica la clave utilizando como Salt el nombre del funcionario
			String clavePlana = nuevo.getContraseña();
			String claveCodificada = passwordEncoder.encodePassword(clavePlana,
					nuevo.getTelefono());
			nuevo.setContraseña(claveCodificada);

			funcionarioManager.crear(nuevo, userDetails.getIdFuncionario());

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"Se agrego exitosamente el funcionario", "300", ESTADO_EXITO,
					OP_ALTA, userDetails.getIdFuncionario(),
					 null, false);

			retorno.put("funcionario", nuevo);
			response.setStatus(201);

		} catch (Exception ex) {

			response.setStatus(422);
			meta = generarMensaje(meta, Constantes.MENSAJE_ERROR,
					"Ocurrio un error al agregar el funcionario", "300",
					ESTADO_ERROR, OP_ALTA, userDetails.getIdFuncionario(),
					 ex, false);

		}
		retorno.put("meta", meta);
		return retorno;
	}

	/**
	 * Mapping para el metodo POST de la vista funcionarioForm. (editar funcionario)
	 * 
	 * @param funcionarioRecibido
	 *            la entidad funcionario a modificar recibida de la vista
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public @ResponseBody
	HashMap<String, Object> editar(@PathVariable("id") Long id,
			@RequestBody Funcionario funcionarioRecibido) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		HashMap<String, Object> meta = new HashMap<String, Object>();

		UserDetailsHornero userDetails = UserDetailsHornero
				.getFuncionarioAutenticado();

		try {

			Funcionario funcionarioPersistido = funcionarioManager.get(id);

			Funcionario ejemplo = new Funcionario();
			ejemplo.setNombre(funcionarioRecibido.getNombre());

			Map<String, Object> funcionarioBD = funcionarioManager.getAtributos(
					ejemplo, atributosFuncionarios.split(","), false, true);

			if (funcionarioBD != null
					&& funcionarioBD.containsKey("id")
					&& !(Long.parseLong(funcionarioBD.get("id").toString()) ==funcionarioPersistido.getId())) {
				throw new ExceptionHornero("nombre","300", "El funcionario con el nombre "
						+ funcionarioRecibido.getNombre() + " ya existe");
			}

			funcionarioPersistido.setNombre(funcionarioRecibido.getNombre());
			funcionarioPersistido.setDocumento(funcionarioRecibido.getDocumento());
			funcionarioPersistido.setTelefono(funcionarioRecibido.getTelefono());



			funcionarioManager.actualizar(funcionarioPersistido,
					userDetails.getIdFuncionario());

			meta = generarMensaje(meta, Constantes.MENSAJE_EXITO,
					"El Funcionario " + funcionarioRecibido.getNombre()
							+ " se modifico exitosamente", "300", ESTADO_EXITO,
					OP_MODIFICACION, userDetails.getIdFuncionario(),
					 null, true);

			retorno.put("funcionario", funcionarioPersistido);

		} catch (Exception ex) {
			meta = generarMensaje(
					meta,
					Constantes.MENSAJE_ERROR,
					"Error al modificar el funcionario "
							+ funcionarioRecibido.getNombre(), "300", ESTADO_ERROR,
					OP_MODIFICACION, userDetails.getIdFuncionario(),
					 ex, true);

		}
		retorno.put("meta", meta);
		return retorno;
	}

	/**
	 * Mapping para el metodo POST de la vista funcionarioForm. (editar funcionario)
	 * 
	 * @param funcionarioRecibido
	 *            la entidad funcionario a modificar recibida de la vista
	 */
	@RequestMapping(value = "/{id}/parametros", method = RequestMethod.PUT)
	public @ResponseBody
	HashMap<String, Object> actualizarParametros(
			@PathVariable("id") Long idFuncionario,
			@ModelAttribute("intervalo") Integer intervaloMuestreo,
			@ModelAttribute("inicio") String horaInicio,
			@ModelAttribute("fin") String horaFin) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		UserDetailsHornero userDetails = UserDetailsHornero
				.getFuncionarioAutenticado();

		try {

			Funcionario funcionarioPersistido = funcionarioManager.get(idFuncionario);

			if (funcionarioPersistido == null) {
				throw new ExceptionHornero("telefono","500", "El telefono no existe");
			}


			funcionarioManager.actualizar(funcionarioPersistido,
					userDetails.getIdFuncionario());

			retorno = generarMensaje(retorno, Constantes.MENSAJE_EXITO,
					"El funcionario " + funcionarioPersistido.getNombre()
							+ " se modifico exitosamente", "300", ESTADO_EXITO,
					OP_MODIFICACION, userDetails.getIdFuncionario(),
					 null, true);

		} catch (Exception e) {
			retorno = generarMensaje(retorno, Constantes.MENSAJE_ERROR,
					"Error al asignar nuevos parametros", "300", ESTADO_ERROR,
					OP_MODIFICACION, userDetails.getIdFuncionario(),
					 e, true);
		}

		return retorno;

	}

	/**
	 * Método utilizado para modificar la clave de un funcionario.
	 * 
	 * @params
	 */
	@RequestMapping(value = "/restablecerclave", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public HashMap<String, Object> restablecerClave(
			@PathVariable("id") Long idfuncionario,
			@ModelAttribute("contrasenaNueva") String contrasenaNueva) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();
		UserDetailsHornero userDetails = UserDetailsHornero
				.getFuncionarioAutenticado();
		String nombre = "";

		try {

			Funcionario funcionario = funcionarioManager.get(idfuncionario);

			if (funcionario.getNombre() != null && !funcionario.getNombre().equals("")) {
				nombre += funcionario.getNombre() + " ";
			}

			// La clave personal del funcionario debe contar con una longitud mínima
			// de 7 caracteres.
			if (contrasenaNueva.length() < 7) {
				throw new ExceptionHornero("clave","500",
						"La nueva Clave Personal de Acceso debe poseer una "
								+ "longitud mínima de 7 caracteres.");
			}

			String clavePlana = contrasenaNueva;
			String claveCodificada = passwordEncoder.encodePassword(clavePlana,
					funcionario.getNombre());
			funcionario.setContraseña(claveCodificada);

			funcionarioManager.actualizar(funcionario, userDetails.getIdFuncionario());

			retorno = generarMensaje(
					retorno,
					Constantes.MENSAJE_EXITO,
					"La Clave del funcionario " + nombre + " ha sido restablecida.",
					"500", ESTADO_EXITO, OP_MODIFICACION,
					userDetails.getIdFuncionario(),	null, true);

		} catch (Exception ex) {
			retorno = generarMensaje(retorno, Constantes.MENSAJE_ERROR,
					"Error al restablecer clave del funcionario " + nombre, "500",
					ESTADO_ERROR, OP_MODIFICACION, userDetails.getIdFuncionario(),
					 ex, true);
		}
		return retorno;
	}

	/**
	 * Metodo para desactivar un funcionario
	 * 
	 * @param id
	 *            el id del funcionario a desactivar
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	HashMap<String, Object> desactivar(@PathVariable("id") Long id) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();

		UserDetailsHornero userDetails = UserDetailsHornero
				.getFuncionarioAutenticado();
		try {
			Map<String, Object> funcionarioBD = funcionarioManager.getAtributos(
					new Funcionario(id), atributosFuncionarios.split(","), false, true);

			if (funcionarioBD != null && funcionarioBD.get("activo").equals("N")) {
				throw new ExceptionHornero(null,"300",
						"El funcionario ya se encuentra desactivado");
			}

			funcionarioManager.desactivar(id, userDetails.getIdFuncionario());

			retorno = generarMensaje(retorno, Constantes.MENSAJE_EXITO,
					"El funcionario se desactivo exitosamente", "300",
					ESTADO_EXITO, OP_DESACTIVACION, userDetails.getIdFuncionario(),
					 null, true);

		} catch (Exception ex) {

			retorno = generarMensaje(retorno, Constantes.MENSAJE_ERROR,
					"Error al desactivar el funcionario", "300", ESTADO_ERROR,
					OP_DESACTIVACION, userDetails.getIdFuncionario(),
					ex, true);

		}

		return retorno;
	}

	/**
	 * Metodo para activar un funcionario
	 * 
	 * @param id
	 *            el id del funcionario a activar
	 */
	@RequestMapping(value = "/{id}/activar", method = RequestMethod.PUT)
	public @ResponseBody
	HashMap<String, Object> activar(@PathVariable("id") Long id) {

		HashMap<String, Object> retorno = new HashMap<String, Object>();

		UserDetailsHornero userDetails = UserDetailsHornero
				.getFuncionarioAutenticado();
		try {
			Map<String, Object> funcionarioBD = funcionarioManager.getAtributos(
					new Funcionario(id), atributosFuncionarios.split(","), false, true);

			if (funcionarioBD != null && funcionarioBD.get("activo").equals("S")) {
				throw new ExceptionHornero(null,"300",
						"El funcionario ya se encuentra activado");
			}

			funcionarioManager.activar(id, userDetails.getIdFuncionario());

			retorno = generarMensaje(retorno, Constantes.MENSAJE_EXITO,
					"El funcionario se activo exitosamente", "300", ESTADO_EXITO,
					OP_DESACTIVACION, userDetails.getIdFuncionario(),
					 null, true);

		} catch (Exception ex) {

			retorno = generarMensaje(retorno, Constantes.MENSAJE_ERROR,
					"Error al activar el funcionario", "300", ESTADO_ERROR,
					OP_DESACTIVACION, userDetails.getIdFuncionario(),
				    ex, true);

		}

		return retorno;
	}

	/**
	 * Recibe un string que es un json y devuelve una entidad con los valores
	 * recibidos
	 * 
	 * @param filtros
	 * @return
	 * @throws Exception
	 */
	public static Funcionario generarEjemplo(FiltroDTO filtro, Funcionario ejemplo)
			throws Exception {

		if (ejemplo == null) {
			ejemplo = new Funcionario();
		}

		for (ReglaDTO regla : filtro.getRules()) {

			if ("id".compareToIgnoreCase(regla.getField()) == 0
					&& regla.getData() != null && !regla.getData().isEmpty()) {
				ejemplo.setId(Long.parseLong(regla.getData()));

			} else if ("nombre".compareToIgnoreCase(regla.getField()) == 0
					&& regla.getData() != null && !regla.getData().isEmpty()) {
				ejemplo.setNombre(regla.getData());

			}
		}
		return ejemplo;

	}

}
