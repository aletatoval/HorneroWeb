/**
 * 
 */
package py.com.hornero.model.ejb;

import javax.ejb.Local;

import py.com.hornero.model.entity.Grupo;

/**
 * @author Miguel
 *
 */
@Local
public interface GrupoManager extends BaseManager<Grupo, Long> {

}
