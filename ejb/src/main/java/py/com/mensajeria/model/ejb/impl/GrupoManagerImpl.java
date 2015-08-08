/**
 * 
 */
package py.com.mensajeria.model.ejb.impl;

import javax.ejb.Stateless;

import py.com.mensajeria.model.ejb.GrupoManager;
import py.com.mensajeria.model.entity.Grupo;

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
