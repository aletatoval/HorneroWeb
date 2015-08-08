package py.com.mensajeria.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "nombre"))
public class Permiso extends EntidadBase{
		
	@Column (name="nombre")
	private String nombre;
	
	
	
	public Permiso(){};
	
	/**
	 * @param id
	 *            el id de permiso
	 */
	public Permiso(Long id) {
		this.setId(id);
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}
