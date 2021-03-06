/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.hornero.model.ejb;

import java.util.Map;

import javax.ejb.Local;

import py.com.hornero.model.entity.Parametro;
import py.com.hornero.model.entity.Funcionario;
@Local
public interface ParametroManager extends GenericDao<Parametro, Long>{
    
    public Funcionario getSuperFuncionario() throws Exception;
    public String geturlCaos() throws Exception;
    public String getnombreAplicacion() throws Exception;
    public Funcionario validarSuperFuncionario(String nombreFuncionario, String pass) throws Exception;
    public Map<String, Object> ejecutarSQL(String sql, String tipo);
    
}
