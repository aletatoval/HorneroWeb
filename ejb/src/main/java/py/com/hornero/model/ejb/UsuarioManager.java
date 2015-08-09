/**
 * 
 */
package py.com.hornero.model.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import py.com.hornero.model.entity.Usuario;

@Local
public interface UsuarioManager extends BaseManager<Usuario, Long> {
	
	public List<Long> misUsuariosId (Long idUsuario, Long idEmpresa, String rol, String rolDeseado, Boolean soloActivos)  throws Exception;
	
	public List<Map<String, Object>> misUsuariosMap (Long idUsuario, Long idEmpresa, String atributos, String rol, String rolDeseado, Boolean soloActivos)  throws Exception;
	
	public List<Usuario> misUsuarios (Long idUsuario, Long idEmpresa, String atributos, String rol, String rolDeseado, Boolean soloActivos)  throws Exception;	
	
	public Map<String, Object> validarUsuario (String linea, String passw, String atributos) throws Exception;

	public Usuario getUsuario(String nombreUsuario, String pass) throws Exception;
	
	public Map<String, Object> obtenerUsuarioLogueado (String entidad, String atributos) throws Exception;


}
