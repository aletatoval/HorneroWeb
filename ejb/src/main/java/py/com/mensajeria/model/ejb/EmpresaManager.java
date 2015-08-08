/**
 * 
 */
package py.com.mensajeria.model.ejb;

import javax.ejb.Local;

import py.com.mensajeria.model.entity.Empresa;

/**
 * @author Hermann Bottger
 * 
 */
@Local
public interface EmpresaManager extends BaseManager<Empresa, Long> {

}
