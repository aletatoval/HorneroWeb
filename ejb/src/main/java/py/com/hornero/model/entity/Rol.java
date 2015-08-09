package py.com.hornero.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
public class Rol extends EntidadBase{
	
	
	
	@Column (name="nombre")
	private String nombre;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "empresa", referencedColumnName = "id")
    private Empresa empresa;
	
	public Rol(){};
	
	/**
	 * @param id
	 *            el id de Empresa
	 */
	public Rol(Long id) {
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
