/**
 * 
 */
package py.com.hornero.model.ejb.impl;

import java.sql.Timestamp;

import javax.ejb.Stateless;

import py.com.hornero.model.ejb.EmpresaManager;
import py.com.hornero.model.entity.Empresa;
@Stateless
public class EmpresaManagerImpl extends BaseManagerImpl<Empresa, Long>
		implements EmpresaManager {
	
	@Override
	protected Class<Empresa> getEntityBeanType() {
		return Empresa.class;
	}
	
	@Override
	public Empresa crear(Empresa entidad, Long idFuncionario) throws Exception {

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
