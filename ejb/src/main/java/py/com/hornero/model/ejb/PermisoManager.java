package py.com.hornero.model.ejb;

import javax.ejb.Local;

import py.com.hornero.model.entity.Permiso;

@Local
public interface PermisoManager extends BaseManager<Permiso, Long>{

}
