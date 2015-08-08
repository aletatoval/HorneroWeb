/**
 * 
 */
package py.com.mensajeria.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;


/**
 * @author Jessica Gonzalez
 * 
 */

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "alias", "empresa" }))
public class Usuario extends EntidadBase {

	@NotNull
	@Column(name = "nombre")
	private String nombre;
	
	@NotNull
	@Column(name = "alias")
	private String alias;

	@NotNull
	@Column(name = "clave")
	private String clave;

	@Column(name = "documento")
	private String documento;

	@Column(name = "telefono")
	private String telefono;

	@Column(name = "email")
	private String email;

	@Column(name = "imei")
	private String imei;

	@Column(name = "imsi")
	private String imsi;

	@Column(name = "tiempo_muestra")
	private Integer tiempoMuestra;

	@Column(name = "hora_inicio_muestra")
	private String horaInicioMuestra;

	@Column(name = "hora_fin_muestra")
	private String horaFinMuestra;
        
	@ManyToOne
	@JoinColumn(name = "rol")
	private Rol rol;
	
	@ManyToOne
	@JoinColumn(name = "empresa")
	private Empresa empresa;
	
	public Usuario() {

	}

	/**
	 * @param id
	 *            el id de Usuario
	 */
	public Usuario(Long id) {
		super(id);
	}

	/**
	 * @return el nombre de Usuario
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            el nombre de Usuario a setear
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return el alias de Usuario
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param linea
	 *            la linea de Usuario a setear
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return la clave de Usuario
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave
	 *            la clave de Usuario a setear
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}

	/**
	 * @return el documento de Usuario
	 */
	public String getDocumento() {
		return documento;
	}

	/**
	 * @param documento
	 *            el documento de Usuario a setear
	 */
	public void setDocumento(String documento) {
		this.documento = documento;
	}

	/**
	 * @return el telefono de Usuario
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            el telefono de Usuario a setear
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return el email de Usuario
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            el email de Usuario a setear
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return el imei de Usuario
	 */
	public String getImei() {
		return imei;
	}

	/**
	 * @param imei
	 *            el imei de Usuario a setear
	 */
	public void setImei(String imei) {
		this.imei = imei;
	}

	/**
	 * @return el imsi de Usuario
	 */
	public String getImsi() {
		return imsi;
	}

	/**
	 * @param imsi
	 *            el imsi de Usuario a setear
	 */
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	/**
	 * @return el tiempoMuestra de Usuario
	 */
	public Integer getTiempoMuestra() {
		return tiempoMuestra;
	}

	/**
	 * @param tiempoMuestra
	 *            el tiempoMuestra de Usuario a setear
	 */
	public void setTiempoMuestra(Integer tiempoMuestra) {
		this.tiempoMuestra = tiempoMuestra;
	}

	/**
	 * @return la horaInicioMuestra de Usuario
	 */
	public String getHoraInicioMuestra() {
		return horaInicioMuestra;
	}

	/**
	 * @param horaInicioMuestra
	 *            la horaInicioMuestra de Usuario a setear
	 */
	public void setHoraInicioMuestra(String horaInicioMuestra) {
		this.horaInicioMuestra = horaInicioMuestra;
	}

	/**
	 * @return la horaFinMuestra de Usuario
	 */
	public String getHoraFinMuestra() {
		return horaFinMuestra;
	}

	/**
	 * @param horaFinMuestra
	 *            la horaFinMuestra de Usuario a setear
	 */
	public void setHoraFinMuestra(String horaFinMuestra) {
		this.horaFinMuestra = horaFinMuestra;
	}

	/**
	 * @return el rol de Usuario
	 */
	public Rol getRol() {
		return rol;
	}

	/**
	 * @param rol
	 *            el rol de Usuario a setear
	 */
	public void setRol(Rol rol) {
		this.rol = rol;
	}

	/**
	 * @return la empresa de Usuario
	 */
	public Empresa getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa
	 *            la empresa de Usuario a setear
	 */
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}