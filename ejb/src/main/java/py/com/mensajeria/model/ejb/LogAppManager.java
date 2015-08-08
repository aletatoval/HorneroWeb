/**
 * 
 */
package py.com.mensajeria.model.ejb;

import javax.ejb.Local;

import py.com.mensajeria.model.entity.LogApp;

/**
 * @author Hermann Bottger
 * 
 */
@Local
public interface LogAppManager extends GenericDao<LogApp, Long> {

}
