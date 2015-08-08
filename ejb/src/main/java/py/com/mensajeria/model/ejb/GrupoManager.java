/**
 * 
 */
package py.com.mensajeria.model.ejb;

import javax.ejb.Local;

import py.com.mensajeria.model.entity.Grupo;

/**
 * @author Miguel
 *
 */
@Local
public interface GrupoManager extends BaseManager<Grupo, Long> {

}
