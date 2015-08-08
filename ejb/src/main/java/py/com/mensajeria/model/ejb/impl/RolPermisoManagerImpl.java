package py.com.mensajeria.model.ejb.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import py.com.mensajeria.model.ejb.PermisoManager;
import py.com.mensajeria.model.ejb.RolManager;
import py.com.mensajeria.model.ejb.RolPermisoManager;
import py.com.mensajeria.model.entity.Empresa;
import py.com.mensajeria.model.entity.Permiso;
import py.com.mensajeria.model.entity.Rol;
import py.com.mensajeria.model.entity.RolPermiso;

/**
 * Session Bean implementation class RolPermisoManagerImpl
 */
@Stateless
public class RolPermisoManagerImpl extends BaseManagerImpl<RolPermiso, Long>
		implements RolPermisoManager {

	private String atributosRolPermiso = "id,permiso.nombre";

	
	@EJB(mappedName = "java:global/mensajeriaapp-ear/mensajeriaapp-ejb/RolManagerImpl")
	private RolManager rolManager;
	
	@EJB(mappedName = "java:global/mensajeriaapp-ear/mensajeriaapp-ejb/PermisoManagerImpl")
	private PermisoManager permisoManager;
	
	@EJB(mappedName = "java:global/mensajeriaapp-ear/mensajeriaapp-ejb/RolPermisoManagerImpl")
	private RolPermisoManager rolPermisoManager;

	@Override
	protected Class<RolPermiso> getEntityBeanType() {
		return RolPermiso.class;
	}

	@Override
	public List<String> listarPermisos(Long idEmpresa, Long idRol)
			throws Exception {

		List<String> permisos = new ArrayList<String>();
		List<Map<String, Object>> listMapRolPermiso = new ArrayList<Map<String, Object>>();
		List<Long> empresasId = new ArrayList<Long>();

		

		RolPermiso ejemplo = new RolPermiso();
		ejemplo.setRol(new Rol(idRol));
		ejemplo.setEmpresa(new Empresa(idEmpresa));

		// los booleans todo false?

		listMapRolPermiso = listAtributos(ejemplo,
				atributosRolPermiso.split(","), false, null, null, null, null,
				false, false,null,null);

		for (Map<String, Object> rp : listMapRolPermiso) {
			permisos.add((String) rp.get("permiso.nombre"));

		}

		return permisos;
	}

	@Override
	public Boolean asociarRolPermiso(Long idEmpresa, Long idRol, List<Long> permisos,
			Long user) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				Rol destino = new Rol();
				if (idRol != null && idRol != 0) {
					destino = rolManager.get(idRol);
					
					RolPermiso ejRolPermiso = new RolPermiso();
					ejRolPermiso.setRol(destino);
					ejRolPermiso.setEmpresa(new Empresa(idEmpresa));
					ejRolPermiso.setActivo("S");
					
					List<Map<String, Object>> misPermisos = this.listAtributos(ejRolPermiso, "id,rol.id,rol.nombre,permiso.id,empresa.id".split(","));
					
					for(Map<String, Object> rpm : misPermisos){				
						boolean existeRelacion = true;
						
						for(Long mod : permisos){
							if (mod != null 
									&& mod.intValue() > 0) {
								
								if(rpm.get("permiso.id").toString().compareToIgnoreCase(String.valueOf(mod)) == 0){
									existeRelacion = false;
								}
							}
							
						}
						
						if (existeRelacion) {
							this.desactivar(Long.valueOf(rpm.get("id").toString()),user);
						}	
					}
					
					
					for (Long mod : permisos) {
						if (mod != null 
								&& mod.intValue() > 0) {
							
							boolean esNuevo = true;
							
							for (Map<String, Object> rpm : misPermisos) {
								
								if (rpm.get("permiso.id").toString().compareToIgnoreCase(String.valueOf(mod)) == 0) {
									esNuevo = false;
									break;
								}
							}
							RolPermiso nuevo  = new RolPermiso();
							
							if (esNuevo) {	
								
								Permiso ejPermiso = permisoManager.get(mod);
								nuevo.setEmpresa(new Empresa(idEmpresa));
								nuevo.setRol(destino);
								nuevo.setPermiso(ejPermiso);

								List<RolPermiso> listaActualizar = this.list(nuevo);
								
								if (listaActualizar == null || listaActualizar.isEmpty()) {									
									this.crear(nuevo, user);
									
								} else {
									
									boolean actualizado = false;
									for (RolPermiso aCrear : listaActualizar) {
										if (actualizado) {
											this.delete(aCrear);
										} else {
											aCrear.setActivo("S");
											this.actualizar(aCrear, user);
											actualizado = true;
										}
									}
								}

							}
						}				
						
					}
										
				}else{
					return false;
				}
				return true;
	}
	
	
}