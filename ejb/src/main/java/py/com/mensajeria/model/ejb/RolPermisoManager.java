package py.com.mensajeria.model.ejb;

import java.util.List;

import javax.ejb.Local;

import py.com.mensajeria.model.entity.Permiso;
import py.com.mensajeria.model.entity.RolPermiso;

@Local
public interface RolPermisoManager extends BaseManager<RolPermiso, Long>{
	
	public List<String> listarPermisos (Long idEmpresa, Long idRol)  throws Exception;
	
	public Boolean asociarRolPermiso(Long idEmpresa,Long idRol, List<Long> permisos, Long user) throws Exception;


}