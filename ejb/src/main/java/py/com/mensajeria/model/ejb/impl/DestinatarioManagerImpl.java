/**
 * 
 */
package py.com.mensajeria.model.ejb.impl;

import javax.ejb.Stateless;

import py.com.mensajeria.model.ejb.DestinatarioManager;
import py.com.mensajeria.model.entity.Destinatario;

/**
 * @author Miguel
 *
 */
@Stateless
public class DestinatarioManagerImpl extends BaseManagerImpl<Destinatario, Long> 
	implements DestinatarioManager{
	
	@Override
	protected Class<Destinatario> getEntityBeanType() {
		return Destinatario.class;
	}
}
