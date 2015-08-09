/**
 * 
 */
package py.com.hornero.model.ejb.impl;

import javax.ejb.Stateless;

import py.com.hornero.model.ejb.GrupoManager;
import py.com.hornero.model.entity.Grupo;

/**
 * @author Miguel
 *
 */
@Stateless
public class GrupoManagerImpl extends BaseManagerImpl<Grupo, Long> 
	implements GrupoManager {
	
	@Override
	protected Class<Grupo> getEntityBeanType() {
		return Grupo.class;
	}

}
