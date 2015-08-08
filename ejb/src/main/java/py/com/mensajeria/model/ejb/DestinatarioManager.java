/**
 * 
 */
package py.com.mensajeria.model.ejb;

import javax.ejb.Local;

import py.com.mensajeria.model.entity.Destinatario;

/**
 * @author Miguel
 *
 */
@Local
public interface DestinatarioManager extends BaseManager<Destinatario, Long>{

}
