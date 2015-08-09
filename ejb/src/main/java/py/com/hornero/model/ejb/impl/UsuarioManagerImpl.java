/**
 * 
 */
package py.com.hornero.model.ejb.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import py.com.hornero.model.ejb.UsuarioManager;
import py.com.hornero.model.entity.Empresa;
import py.com.hornero.model.entity.Usuario;
import py.com.hornero.utils.Constantes;
import py.com.hornero.utils.ExceptionHornero;
import py.com.hornero.utils.json.JSONObject;

@Stateless
public class UsuarioManagerImpl extends BaseManagerImpl<Usuario, Long>
		implements UsuarioManager {

	
	@Override
	protected Class<Usuario> getEntityBeanType() {
		return Usuario.class;
	}

	public List<Long> misUsuariosId(Long idUsuario, Long idEmpresa, String rol,
										String rolDeseado, Boolean soloActivos) throws Exception {

		String atributos = "id";

		List<Map<String, Object>> misUsuarios = misUsuariosMap(idUsuario,
									idEmpresa, atributos, rol, rolDeseado, soloActivos);
		List<Long> ids = new ArrayList<Long>();

		for (Map<String, Object> usuario : misUsuarios) {
			if (usuario.containsKey("id") && usuario.get("id") != null
					&& Long.parseLong(usuario.get("id").toString()) > 0) {
				
				ids.add(Long.parseLong(usuario.get("id").toString()));
			} 
		}

		return ids;
	}

	public List<Map<String, Object>> misUsuariosMap(Long idUsuario,
			Long idEmpresa, String atributos, String rol, String rolDeseado,
			Boolean soloActivos) throws Exception {

		if (atributos == null || atributos.compareTo("") == 0){
			atributos = "id,nombre,alias,documento,activo,empresa.id,empresa.nombre,empresa.alias";
		}
		
		List<Map<String, Object>> misUsuarios = null;
		misUsuarios = new ArrayList<Map<String, Object>>();
		
		Usuario ejUser = new Usuario();
		if (soloActivos) {
			ejUser.setActivo("S");
		}

		ejUser.setEmpresa(new Empresa(idEmpresa));
		if (rolDeseado != null && !rolDeseado.isEmpty()) {
//			ejUser.setNombreRol(rolDeseado);
		}
		
		if (rol != null && rol.compareTo(Constantes.ROLADMINSTRADOR) == 0) {
			misUsuarios = listAtributos(ejUser, atributos.split(","),
							"alias,nombre".split(","), "asc,asc".split(","));			
						
		} else {
			List<Long> misIdUsuarios = new ArrayList<Long>();
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
			misIdUsuarios.add(idUsuario);
			misUsuarios = listAtributos(ejUser, atributos.split(","), true, null, null, 
									"alias,nombre".split(","), "asc,asc".split(","), true, true,
									null, null, "id", misIdUsuarios, "OP_IN");			
		}

		return misUsuarios;
	}

	public Map<String, Object> validarUsuario(String alias, String passw,
			String atributos) throws Exception {

		Map<String, Object> retorno = new HashMap<String, Object>();
		Map<String, Object> usuario = null;

		if (atributos == null || atributos.isEmpty()) {
			atributos = "id,nombre,alias,documento,activo,clave,rol.id,empresa.id,empresa.nombre,empresa.alias,empresa.activo";
		}

		Usuario usuarioEjemplo = new Usuario();
		usuarioEjemplo.setAlias(alias);

		usuario = this.getAtributos(usuarioEjemplo, atributos.split(","), false, true);
		if (usuario == null) {
			throw new ExceptionHornero("alias","-2",
					"El alias no corresponde a ningun usuario");
		}

		if (!usuario.containsKey("empresa.activo")
				|| usuario.get("empresa.activo").toString()
						.compareToIgnoreCase("S") != 0) {
			throw new ExceptionHornero("empresa","-3",
					"La empresa del usuario no se encuetra activa");
		}

		if (!usuario.containsKey("activo")
				|| usuario.get("activo").toString()
						.compareToIgnoreCase("S") != 0) {
			throw new ExceptionHornero(null,"-4",
					"El usuario actualmente no se encuestra vigente");
		}

		if (usuario.get("clave").toString().compareToIgnoreCase(passw) != 0) {
			throw new ExceptionHornero(null,"-5",
					"La contraseña ingresada no es correcta");
		}

		if (!usuario.containsKey("rol.id")
				|| usuario.get("rol.id").toString()
						.compareToIgnoreCase("") == 0) {
			throw new ExceptionHornero(null,"0",
					"El usuario no tiene asignado ningun Rol");
		}


		retorno.put("error", false);
		retorno.put("mensaje", "Usuario autenticado exitosamente");
		retorno.put("resultado", usuario);
		return retorno;
	}

	@Override
	public Usuario getUsuario(String nombreUsuario, String pass)
			throws Exception {
		Usuario usuarioEjemplo = new Usuario();
		usuarioEjemplo.setAlias(nombreUsuario);
		usuarioEjemplo.setClave(pass);

		Usuario resultado = this.get(usuarioEjemplo);
		return resultado;

	}

	@Override
	public List<Usuario> misUsuarios(Long idUsuario, Long idEmpresa,
			String atributos, String rol, String rolDeseado, Boolean soloActivos)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Map<String, Object> obtenerUsuarioLogueado (String entidad, String atributos) throws Exception {
		
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

		retornoValidacion = this.validarUsuario(linea, clave, atributos);
		
		return retornoValidacion;
	}


}
