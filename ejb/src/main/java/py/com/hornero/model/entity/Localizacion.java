/**
 * 
 */
package py.com.hornero.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
@Entity
@Table
public class Localizacion  extends EntidadBase {

	private static final long serialVersionUID = -7008116486028882511L;

	
	@NotNull
	@Column(name = "latitud")
	private Double latitud;

	@NotNull
	@Column(name = "longitud")
	private Double longitud;

	@Column(name = "precision")
	private Double precision;

	@Column(name = "imei")
	private String imei;

	@Column(name = "fecha")
	@Temporal(TemporalType.DATE)
	private Date fecha;

	@Column(name = "hora")
	@Temporal(TemporalType.TIME)
	private Date hora;
	
	@Column(name = "fecha_sincronizacion")
	private Timestamp fechaSincronizacion;	

	@ManyToOne
	@JoinColumn(name = "funcionario")
	private Funcionario Funcionario;
	

	public Localizacion() {

	}

	/**
	 * @param id
	 *            el id de Localizacion
	 */
	public Localizacion(Long id) {
		super(id);
	}


	/**
	 * @return la latitud de Localizacion
	 */
	public Double getLatitud() {
		return latitud;
	}

	/**
	 * @param latitud
	 *            la latitud de Localizacion a setear
	 */
	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	/**
	 * @return la longitud de Localizacion
	 */
	public Double getLongitud() {
		return longitud;
	}

	/**
	 * @param longitud
	 *            la longitud de Localizacion a setear
	 */
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	/**
	 * @return la precision de Localizacion
	 */
	public Double getPrecision() {
		return precision;
	}

	/**
	 * @param precision
	 *            la precision de Localizacion a setear
	 */
	public void setPrecision(Double precision) {
		this.precision = precision;
	}

	/**
	 * @return el imei de Localizacion
	 */
	public String getImei() {
		return imei;
	}

	/**
	 * @param imei
	 *            el imei de Localizacion a setear
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}


	public Funcionario getFuncionario() {
		return Funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		Funcionario = funcionario;
	}

	/**
	 * @return the fechaSincronizacion
	 */
	public Timestamp getFechaSincronizacion() {
		return fechaSincronizacion;
	}

	/**
	 * @param fechaSincronizacion the fechaSincronizacion to set
	 */
	public void setFechaSincronizacion(Timestamp fechaSincronizacion) {
		this.fechaSincronizacion = fechaSincronizacion;
	}

	
	
}
