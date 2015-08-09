/**
 * 
 */
package py.com.hornero.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

@MappedSuperclass
public class EntidadBase implements Serializable {

	private static final long serialVersionUID = -9149680520407250259L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

//    @NotNull
	@Size(max = 1)
	@Column(name = "activo")
	private String activo;

//    @NotNull
	@Column(name = "fecha_creacion")
	private Timestamp fechaCreacion;

//    @NotNull
	@Column(name = "id_usuario_creacion")
	private Long idUsuarioCreacion;

	@Column(name = "fecha_modificacion")
	private Timestamp fechaModificacion;

	@Column(name = "id_usuario_modificacion")
	private Long idUsuarioModificacion;

	@Column(name = "origen_modificacion")
	private String origenModificacion;

	@Column(name = "fecha_eliminacion")
	private Timestamp fechaEliminacion;

	@Column(name = "id_usuario_eliminacion")
	private Long idUsuarioEliminacion;

	public EntidadBase() {

	}

	/**
	 * @param id
	 *            el id de EntidadBase
	 */
	public EntidadBase(Long id) {
		this.setId(id);
	}

	/**
	 * @return el id de EntidadBase
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            el id de EntidadBase a setear
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return el activo de EntidadBase
	 */
	public String getActivo() {
		return activo;
	}

	/**
	 * @param activo
	 *            el activo de EntidadBase a setear
	 */
	public void setActivo(String activo) {
		this.activo = activo;
	}

	/**
	 * @return la fechaCreacion de EntidadBase
	 */
	public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion
	 *            la fechaCreacion de EntidadBase a setear
	 */
	public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return el idUsuarioCreacion de EntidadBase
	 */
	public Long getIdUsuarioCreacion() {
		return idUsuarioCreacion;
	}

	/**
	 * @param idUsuarioCreacion
	 *            el idUsuarioCreacion de EntidadBase a setear
	 */
	public void setIdUsuarioCreacion(Long idUsuarioCreacion) {
		this.idUsuarioCreacion = idUsuarioCreacion;
	}

	/**
	 * @return la fechaModificacion de EntidadBase
	 */
	public Timestamp getFechaModificacion() {
		return fechaModificacion;
	}

	/**
	 * @param fechaModificacion
	 *            la fechaModificacion de EntidadBase a setear
	 */
	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	/**
	 * @return el idUsuarioModificacion de EntidadBase
	 */
	public Long getIdUsuarioModificacion() {
		return idUsuarioModificacion;
	}

	/**
	 * @param idUsuarioModificacion
	 *            el idUsuarioModificacion de EntidadBase a setear
	 */
	public void setIdUsuarioModificacion(Long idUsuarioModificacion) {
		this.idUsuarioModificacion = idUsuarioModificacion;
	}

	/**
	 * @return el origenModificacion de EntidadBase
	 */
	public String getOrigenModificacion() {
		return origenModificacion;
	}

	/**
	 * @param origenModificacion
	 *            el origenModificacion de EntidadBase a setear
	 */
	public void setOrigenModificacion(String origenModificacion) {
		this.origenModificacion = origenModificacion;
	}

	/**
	 * @return la fechaEliminacion de EntidadBase
	 */
	public Timestamp getFechaEliminacion() {
		return fechaEliminacion;
	}

	/**
	 * @param fechaEliminacion
	 *            la fechaEliminacion de EntidadBase a setear
	 */
	public void setFechaEliminacion(Timestamp fechaEliminacion) {
		this.fechaEliminacion = fechaEliminacion;
	}

	/**
	 * @return el idUsuarioEliminacion de EntidadBase
	 */
	public Long getIdUsuarioEliminacion() {
		return idUsuarioEliminacion;
	}

	/**
	 * @param idUsuarioEliminacion
	 *            el idUsuarioEliminacion de EntidadBase a setear
	 */
	public void setIdUsuarioEliminacion(Long idUsuarioEliminacion) {
		this.idUsuarioEliminacion = idUsuarioEliminacion;
	}

}
