/**
 * 
 */
package py.com.hornero.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * @author Miguel
 *
 */
@Entity
public class Grupo  extends EntidadBase{
	
	@NotNull
	@Column(name = "nombre")
	private String nombre;

	
	@ManyToOne(optional = false)
    @JoinColumn(name = "empresa", referencedColumnName = "id")
    private Empresa empresa;

	/**
	 * 
	 */
	public Grupo() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param id
	 *            el id de Grupo
	 */
	public Grupo(Long id) {
		super(id);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	

}
