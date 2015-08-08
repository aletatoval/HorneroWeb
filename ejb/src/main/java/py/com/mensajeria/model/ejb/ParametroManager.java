/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.mensajeria.model.ejb;

import java.util.Map;

import javax.ejb.Local;

import py.com.mensajeria.model.entity.Parametro;
import py.com.mensajeria.model.entity.Usuario;

/**
 *
 * @author Hermann Bottger
 */
@Local
public interface ParametroManager extends GenericDao<Parametro, Long>{
    
    public Usuario getSuperUsuario() throws Exception;
    public String geturlCaos() throws Exception;
    public String getnombreAplicacion() throws Exception;
    public Usuario validarSuperUsuario(String nombreUsuario, String pass) throws Exception;
    public Map<String, Object> ejecutarSQL(String sql, String tipo);
    
}
