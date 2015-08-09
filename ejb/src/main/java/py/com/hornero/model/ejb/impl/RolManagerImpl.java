package py.com.hornero.model.ejb.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import py.com.hornero.model.ejb.RolManager;
import py.com.hornero.model.entity.Empresa;
import py.com.hornero.model.entity.Rol;

/**
 * Session Bean implementation class RolManagerImpl
 */
@Stateless
public class RolManagerImpl extends BaseManagerImpl<Rol, Long> 
		implements RolManager {

	@Override
	protected Class<Rol> getEntityBeanType() {
		return Rol.class;
	}

	@Override
	public List<Long> misIdRoles(Long idEmpresa, String atributos,
			String atributosBusqueda, String valorFiltro, Boolean soloActivos)
			throws Exception {
		// TODO Auto-generated method stub
		List<Long> misRoles = new ArrayList<Long>();

		if ((idEmpresa != null && idEmpresa.toString().compareToIgnoreCase("") != 0)) {
			
			Rol ejRol = new Rol();
			ejRol.setEmpresa(new Empresa(idEmpresa));
			
			List<Map<String, Object>> modulosMap = this.listAtributos(ejRol, "id,empresa.id".split(","));
			
			for(Map<String, Object> rpm : modulosMap){
				misRoles.add(Long.valueOf(rpm.get("id").toString()));
			}
			
		} 

		return misRoles;
	}
	
	
}