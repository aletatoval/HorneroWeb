/**
 * 
 */
package py.com.hornero.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;




@Entity
public class Destinatario extends EntidadBase {

	@NotNull
	@Column(name = "nombre")
	private String nombre;

	@NotNull
	@Column(name = "destinatario")
	private Integer destinatario;
	
	
	public Destinatario() {

	}

	/**
	 * @param id
	 *            el id de Destinatario
	 */
	public Destinatario(Long id) {
		super(id);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Integer destinatario) {
		this.destinatario = destinatario;
	}

	
	
}
