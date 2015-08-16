/**
 * 
 */
package py.com.hornero.model.ejb.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import py.com.hornero.model.ejb.FuncionarioManager;
import py.com.hornero.model.entity.Funcionario;
import py.com.hornero.utils.Constantes;
import py.com.hornero.utils.ExceptionHornero;
import py.com.hornero.utils.json.JSONObject;

@Stateless
public class FuncionarioManagerImpl extends BaseManagerImpl<Funcionario, Long>
		implements FuncionarioManager {

	
	@Override
	protected Class<Funcionario> getEntityBeanType() {
		return Funcionario.class;
	}

	public List<Long> misFuncionariosId(Long idFuncionario,  String rol,
										String rolDeseado, Boolean soloActivos) throws Exception {

		String atributos = "id";

		List<Map<String, Object>> misFuncionarios = misFuncionariosMap(idFuncionario,
									 atributos, rol, rolDeseado, soloActivos);
		List<Long> ids = new ArrayList<Long>();

		for (Map<String, Object> funcionario : misFuncionarios) {
			if (funcionario.containsKey("id") && funcionario.get("id") != null
					&& Long.parseLong(funcionario.get("id").toString()) > 0) {
				
				ids.add(Long.parseLong(funcionario.get("id").toString()));
			} 
		}

		return ids;
	}

	public List<Map<String, Object>> misFuncionariosMap(Long idFuncionario,
			 String atributos, String rol, String rolDeseado,
			Boolean soloActivos) throws Exception {

		if (atributos == null || atributos.compareTo("") == 0){
			atributos = "id,nombre,alias,documento,activo,empresa.id,empresa.nombre,empresa.alias";
		}
		
		List<Map<String, Object>> misFuncionarios = null;
		misFuncionarios = new ArrayList<Map<String, Object>>();
		
		Funcionario ejUser = new Funcionario();
		if (soloActivos) {
			ejUser.setActivo("S");
		}

		if (rolDeseado != null && !rolDeseado.isEmpty()) {
//			ejUser.setNombreRol(rolDeseado);
		}
		
		if (rol != null && rol.compareTo(Constantes.ROLADMINSTRADOR) == 0) {
			misFuncionarios = listAtributos(ejUser, atributos.split(","),
							"alias,nombre".split(","), "asc,asc".split(","));			
						
		} else {
			List<Long> misIdFuncionarios = new ArrayList<Long>();
//			Map<String,Object> usuario = getAtributos(new Usuario(idUsuario), "supervisados".split(","));
//			
//			if (usuario.containsKey("supervisados") && usuario.get("supervisados") != null){
//				String[] supervisados =usuario.get("supervisados").toString().split(",");
//				
//				for(int i=0; i < supervisados.length; i++){
//					if (supervisados[i] != null && supervisados[i].compareTo("") != 0 && Long.parseLong(supervisados[i]) > 0){
//						misIdUsuarios.add(Long.parseLong(supervisados[i]));
//					}
//				}
//			}
			misIdFuncionarios.add(idFuncionario);
			misFuncionarios = listAtributos(ejUser, atributos.split(","), true, null, null, 
									"alias,nombre".split(","), "asc,asc".split(","), true, true,
									null, null, "id", misIdFuncionarios, "OP_IN");			
		}

		return misFuncionarios;
	}

	public Map<String, Object> validarFuncionario(String alias, String passw,
			String atributos) throws Exception {

		Map<String, Object> retorno = new HashMap<String, Object>();
		Map<String, Object> funcionario = null;

		if (atributos == null || atributos.isEmpty()) {
			atributos = "id,nombre,alias,documento,activo,clave,rol.id,empresa.id,empresa.nombre,empresa.alias,empresa.activo";
		}

		Funcionario usuarioEjemplo = new Funcionario();

		funcionario = this.getAtributos(usuarioEjemplo, atributos.split(","), false, true);
		if (funcionario == null) {
			throw new ExceptionHornero("alias","-2",
					"El alias no corresponde a ningun usuario");
		}

		if (!funcionario.containsKey("empresa.activo")
				|| funcionario.get("empresa.activo").toString()
						.compareToIgnoreCase("S") != 0) {
			throw new ExceptionHornero("empresa","-3",
					"La empresa del usuario no se encuetra activa");
		}

		if (!funcionario.containsKey("activo")
				|| funcionario.get("activo").toString()
						.compareToIgnoreCase("S") != 0) {
			throw new ExceptionHornero(null,"-4",
					"El usuario actualmente no se encuestra vigente");
		}

		if (funcionario.get("clave").toString().compareToIgnoreCase(passw) != 0) {
			throw new ExceptionHornero(null,"-5",
					"La contraseña ingresada no es correcta");
		}

		if (!funcionario.containsKey("rol.id")
				|| funcionario.get("rol.id").toString()
						.compareToIgnoreCase("") == 0) {
			throw new ExceptionHornero(null,"0",
					"El usuario no tiene asignado ningun Rol");
		}


		retorno.put("error", false);
		retorno.put("mensaje", "Funcionario autenticado exitosamente");
		retorno.put("resultado", funcionario);
		return retorno;
	}

	@Override
	public Funcionario getFuncionario(String nombreFuncionario, String pass)
			throws Exception {
		Funcionario funcionarioEjemplo = new Funcionario();

		Funcionario resultado = this.get(funcionarioEjemplo);
		return resultado;

	}

	@Override
	public List<Funcionario> misFuncionarios(Long idFuncionario, String atributos,
			String rol, String rolDeseado, Boolean soloActivos)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Map<String, Object> obtenerFuncionarioLogueado (String entidad, String atributos) throws Exception {
		
		Map<String, Object> retornoValidacion = null;
		String linea = null;
		String clave = null;
		
		JSONObject objetoJson = new JSONObject(entidad);

		if (objetoJson.has("alias")) {
			linea = objetoJson.getString("alias");
		}
		if (objetoJson.has("clave")) {
			clave = objetoJson.getString("clave");
		}
		
		 if (linea == null ||  clave == null) {
			 throw new ExceptionHornero(null,"-1", "Información de acceso incompleta");
		 }

		retornoValidacion = this.validarFuncionario(linea, clave, atributos);
		
		return retornoValidacion;
	}



}
