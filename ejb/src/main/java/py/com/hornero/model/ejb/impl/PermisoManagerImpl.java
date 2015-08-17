package py.com.hornero.model.ejb.impl;

import java.sql.Timestamp;

import javax.ejb.Stateless;

import py.com.hornero.model.ejb.PermisoManager;
import py.com.hornero.model.entity.Permiso;
@Stateless
public class PermisoManagerImpl extends BaseManagerImpl<Permiso, Long> 
		implements PermisoManager {

	@Override
	protected Class<Permiso> getEntityBeanType() {
		return Permiso.class;
	}
	
	@Override
	public Permiso crear(Permiso entidad, Long idFuncionario) throws Exception {
		
		if (entidad.getFechaCreacion() == null){
			entidad.setFechaCreacion(new Timestamp(System.currentTimeMillis()));	
		}
		
		if (entidad.getIdUsuarioCreacion() == null){
			entidad.setIdUsuarioCreacion(idFuncionario);	
		}
		
		entidad.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
		entidad.setIdUsuarioModificacion(idFuncionario);
		
		
		entidad.setActivo("S");
		this.save(entidad);
		
		return entidad;
	}
}