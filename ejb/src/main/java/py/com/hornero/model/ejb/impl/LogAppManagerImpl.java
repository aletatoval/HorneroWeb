/**
 * 
 */
package py.com.hornero.model.ejb.impl;

import javax.ejb.Stateless;

import py.com.hornero.model.ejb.LogAppManager;
import py.com.hornero.model.entity.LogApp;
@Stateless
public class LogAppManagerImpl extends GenericDaoImpl<LogApp, Long>
		implements LogAppManager {
	@Override
	protected Class<LogApp> getEntityBeanType() {
		return LogApp.class;
	}

}
