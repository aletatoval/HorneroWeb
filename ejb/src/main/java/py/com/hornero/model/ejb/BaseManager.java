package py.com.hornero.model.ejb;

import java.io.Serializable;

import py.com.hornero.model.entity.EntidadBase;


public interface BaseManager<T extends EntidadBase, ID extends Serializable>
		extends GenericDao<T, ID> {

	public T crear(T entidad, Long idUsuario) throws Exception;

	public T actualizar(T e, Long idUsuario) throws Exception;

	public T desactivar(ID id, Long idUsuario) throws Exception;

	public T activar(ID id, Long idUsuario) throws Exception;
}
