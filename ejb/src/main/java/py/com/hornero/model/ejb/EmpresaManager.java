/**
 * 
 */
package py.com.hornero.model.ejb;

import javax.ejb.Local;

import py.com.hornero.model.entity.Empresa;

/**
 * @author Hermann Bottger
 * 
 */
@Local
public interface EmpresaManager extends BaseManager<Empresa, Long> {

}
