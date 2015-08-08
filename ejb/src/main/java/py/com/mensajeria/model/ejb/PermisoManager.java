package py.com.mensajeria.model.ejb;

import javax.ejb.Local;

import py.com.mensajeria.model.entity.Permiso;

@Local
public interface PermisoManager extends BaseManager<Permiso, Long>{

}
