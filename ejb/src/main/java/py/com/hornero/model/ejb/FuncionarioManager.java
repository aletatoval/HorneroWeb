/**
 * 
 */
package py.com.hornero.model.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import py.com.hornero.model.entity.Funcionario;

@Local
public interface FuncionarioManager extends BaseManager<Funcionario, Long> {
	
	public List<Long> misFuncionariosId (Long idFuncionario, String rol, String rolDeseado, Boolean soloActivos)  throws Exception;
	
	public List<Map<String, Object>> misFuncionariosMap (Long idFuncionario, String atributos, String rol, String rolDeseado, Boolean soloActivos)  throws Exception;
	
	public List<Funcionario> misFuncionarios (Long idFuncionario,  String atributos, String rol, String rolDeseado, Boolean soloActivos)  throws Exception;	
	
	public Map<String, Object> validarFuncionario (String linea, String passw, String atributos) throws Exception;

	public Funcionario getFuncionario(String nombreFuncionario, String pass) throws Exception;
	
	public Map<String, Object> obtenerFuncionarioLogueado (String entidad, String atributos) throws Exception;


}
