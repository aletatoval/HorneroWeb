/**
 *
 */
package py.com.mensajeria.model.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Hermann Bottger
 *
 */

@Entity
@Table(name="rol_permiso")
public class RolPermiso extends EntidadBase {

    private static final long serialVersionUID = 1L;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "empresa", referencedColumnName = "id")
    private Empresa empresa;
   
    @ManyToOne(optional = false)
    @JoinColumn(name = "permiso", referencedColumnName = "id")
    private Permiso permiso; 
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "rol", referencedColumnName = "id")
    private Rol rol; 
    
    @Transient
	private List<Long> permisos;
    
	public RolPermiso() {
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Permiso getPermiso() {
		return permiso;
	}

	public void setPermiso(Permiso permiso) {
		this.permiso = permiso;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public List<Long> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<Long> permisos) {
		this.permisos = permisos;
	}

    
}
