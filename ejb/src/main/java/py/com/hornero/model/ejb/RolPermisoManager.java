package py.com.hornero.model.ejb;

import java.util.List;

import javax.ejb.Local;

import py.com.hornero.model.entity.Permiso;
import py.com.hornero.model.entity.RolPermiso;

@Local
public interface RolPermisoManager extends BaseManager<RolPermiso, Long>{
	
	public List<String> listarPermisos (Long idEmpresa, Long idRol)  throws Exception;
	
	public Boolean asociarRolPermiso(Long idEmpresa,Long idRol, List<Long> permisos, Long user) throws Exception;


}