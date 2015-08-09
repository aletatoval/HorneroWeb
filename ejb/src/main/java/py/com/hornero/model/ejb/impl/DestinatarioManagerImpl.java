/**
 * 
 */
package py.com.hornero.model.ejb.impl;

import javax.ejb.Stateless;

import py.com.hornero.model.ejb.DestinatarioManager;
import py.com.hornero.model.entity.Destinatario;

@Stateless
public class DestinatarioManagerImpl extends BaseManagerImpl<Destinatario, Long> 
	implements DestinatarioManager{
	
	@Override
	protected Class<Destinatario> getEntityBeanType() {
		return Destinatario.class;
	}
}
