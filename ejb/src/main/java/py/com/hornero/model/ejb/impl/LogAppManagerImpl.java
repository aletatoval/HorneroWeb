/**
 * 
 */
package py.com.hornero.model.ejb.impl;

import javax.ejb.Stateless;

import py.com.hornero.model.ejb.LogAppManager;
import py.com.hornero.model.entity.LogApp;

/**
 * @author Hermann Bottger
 * 
 */
@Stateless
public class LogAppManagerImpl extends GenericDaoImpl<LogApp, Long>
		implements LogAppManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * py.com.personal.visitas.model.ejb.impl.GenericDaoImpl#getEntityBeanType()
	 */
	@Override
	protected Class<LogApp> getEntityBeanType() {
		return LogApp.class;
	}

}
