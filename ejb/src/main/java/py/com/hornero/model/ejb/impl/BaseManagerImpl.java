/**
 * 
 */
package py.com.hornero.model.ejb.impl;

import java.io.Serializable;
import java.sql.Timestamp;

import py.com.hornero.model.ejb.BaseManager;
import py.com.hornero.model.entity.EntidadBase;

/**
 * @author Hermann Bottger
 * 
 */
public abstract class BaseManagerImpl<T extends EntidadBase, ID extends Serializable>
		extends GenericDaoImpl<T, ID> implements BaseManager<T, ID> {

	@Override
	public T crear(T entidad, Long idUsuario) throws Exception {

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

	@Override
	public T actualizar(T entidad, Long idUsuario) throws Exception {

		entidad.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
		entidad.setIdUsuarioModificacion(idUsuario);
		entidad.setActivo("S");
		entidad.setOrigenModificacion("W");	

		this.update(entidad);
		return entidad;
	}

	@Override
	public T desactivar(ID id, Long idUsuario) throws Exception {

		T entidad = this.get(id);
		entidad.setActivo("N");
		entidad.setFechaEliminacion(new Timestamp(System.currentTimeMillis()));
		entidad.setIdUsuarioEliminacion(idUsuario);

		entidad.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
		entidad.setIdUsuarioModificacion(idUsuario);
		
		entidad.setOrigenModificacion("W");	

		this.update(entidad);
		return entidad;
	}

	@Override
	public T activar(ID id, Long idUsuario) throws Exception {

		T entidad = this.get(id);
		entidad.setActivo("S");
		entidad.setFechaEliminacion(null);
		entidad.setIdUsuarioEliminacion(null);

		entidad.setFechaModificacion(new Timestamp(System.currentTimeMillis()));
		entidad.setIdUsuarioModificacion(idUsuario);

		entidad.setOrigenModificacion("W");	

		this.update(entidad);
		return entidad;
	}

}
