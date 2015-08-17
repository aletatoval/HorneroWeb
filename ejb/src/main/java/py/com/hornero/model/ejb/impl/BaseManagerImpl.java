/**
 * 
 */
package py.com.hornero.model.ejb.impl;

import java.io.Serializable;
import java.sql.Timestamp;

import py.com.hornero.model.ejb.BaseManager;
import py.com.hornero.model.entity.EntidadBase;

public abstract class BaseManagerImpl<T extends EntidadBase, ID extends Serializable>
		extends GenericDaoImpl<T, ID> implements BaseManager<T, ID> {

/**
 * 
 **/
	@Override
	public T crear(T entidad, Long idFuncionario) throws Exception {

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

	@Override
	public T actualizar(T entidad, Long idFuncionario) throws Exception {

		entidad.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
		entidad.setIdUsuarioModificacion(idFuncionario);
		entidad.setActivo("S");

		this.update(entidad);
		return entidad;
	}

	@Override
	public T desactivar(ID id, Long idFuncionario) throws Exception {

		T entidad = this.get(id);
		entidad.setActivo("N");

		entidad.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
		entidad.setIdUsuarioModificacion(idFuncionario);
		

		this.update(entidad);
		return entidad;
	}

	@Override
	public T activar(ID id, Long idFuncionario) throws Exception {

		T entidad = this.get(id);
		entidad.setActivo("S");

		entidad.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
		entidad.setIdUsuarioModificacion(idFuncionario);

		this.update(entidad);
		return entidad;
	}

}
