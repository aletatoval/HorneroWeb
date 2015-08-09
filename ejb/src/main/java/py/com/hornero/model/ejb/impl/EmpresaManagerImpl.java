/**
 * 
 */
package py.com.hornero.model.ejb.impl;

import java.sql.Timestamp;

import javax.ejb.Stateless;

import py.com.hornero.model.ejb.EmpresaManager;
import py.com.hornero.model.entity.Empresa;

/**
 * @author Hermann Bottger
 * 
 */
@Stateless
public class EmpresaManagerImpl extends BaseManagerImpl<Empresa, Long>
		implements EmpresaManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * py.com.personal.visitas.model.ejb.impl.GenericDaoImpl#getEntityBeanType()
	 */
	
	@Override
	protected Class<Empresa> getEntityBeanType() {
		return Empresa.class;
	}
	
	@Override
	public Empresa crear(Empresa entidad, Long idUsuario) throws Exception {

		if (entidad.getFechaCreacion() == null){
			entidad.setFechaCreacion(new Timestamp(System.currentTimeMillis()));	
		}
		
		if (entidad.getIdUsuarioCreacion() == null){
			entidad.setIdUsuarioCreacion(idUsuario);	
		}
		
		entidad.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
		entidad.setIdUsuarioModificacion(idUsuario);
		
		if (entidad.getOrigenModificacion() == null){
			entidad.setOrigenModificacion("W");	
		}
		
		entidad.setActivo("S");

		this.save(entidad);
		
		return entidad;
	}
}
