package py.com.mensajeria.model.ejb;

import java.util.List;

import javax.ejb.Local;

import py.com.mensajeria.model.entity.Rol;

@Local
public interface RolManager extends BaseManager<Rol, Long>{

	public List<Long> misIdRoles(Long idEmpresa, String atributos, String atributosBusqueda, String valorFiltro, Boolean soloActivos) throws Exception;

}
