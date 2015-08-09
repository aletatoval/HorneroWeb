/**
 * 
 */
package py.com.hornero.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;



/**
 * @author Miguel
 *
 */
@Entity
public class Destinatario extends EntidadBase {

	@NotNull
	@Column(name = "nombre")
	private String nombre;

	@NotNull
	@Column(name = "destinatario")
	private Integer destinatario;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "empresa", referencedColumnName = "id")
    private Empresa empresa;
	
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

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	
}
