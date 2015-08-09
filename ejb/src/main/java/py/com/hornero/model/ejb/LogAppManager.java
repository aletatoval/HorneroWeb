/**
 * 
 */
package py.com.hornero.model.ejb;

import javax.ejb.Local;

import py.com.hornero.model.entity.LogApp;

/**
 * @author Hermann Bottger
 * 
 */
@Local
public interface LogAppManager extends GenericDao<LogApp, Long> {

}
