/**
 * 
 */
package py.com.hornero.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "alias"))
public class Empresa extends EntidadBase {

	@NotNull
	@NotEmpty
	@Column(name = "nombre")
	private String nombre;

	@NotNull
	@NotEmpty
	@Column(name = "alias")
	private String alias;

	@NotNull
	@NotEmpty
	@Column(name = "ruc")
	private String ruc;

	@NotNull
	@Column(name= "codigo_acceso")
	private String codigoAcceso;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "telefono")
	private String telefono;

	@Column(name = "email")
	private String email;

	@Column(name = "soporte_nombre")
	private String soporteNombre;

	@Column(name = "soporte_telefono")
	private String soporteTelefono;

	@Column(name = "soporte_email")
	private String soporteEmail;

	@Column(name = "latitud")
	private Double latitud;

	@Column(name = "longitud")
	private Double longitud;


	public Empresa() {

	}

	/**
	 * @param id
	 *            el id de Empresa
	 */
	public Empresa(Long id) {
		super(id);
	}

	/**
	 * @return el nombre de Empresa
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            el nombre de Empresa a setear
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return el alias de Empresa
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias
	 *            el alias de Empresa a setear
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return el ruc de Empresa
	 */
	public String getRuc() {
		return ruc;
	}

	/**
	 * @param ruc
	 *            el ruc de Empresa a setear
	 */
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	
	/**
	 * @return 
	 * 	 		el codigo de la empresa            
	 */
	public String getCodigoAcceso() {
		return codigoAcceso;
	}
	

	/**
	 * @param codigo
	 *            el codigo de Empresa a setear
	 */
	public void setCodigoAcceso(String codigoAcceso) {
		this.codigoAcceso = codigoAcceso;
	}

	/**
	 * @return la descripcion de Empresa
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            la descripcion de Empresa a setear
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return la direccion de Empresa
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            la direccion de Empresa a setear
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return el telefono de Empresa
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            el telefono de Empresa a setear
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return el email de Empresa
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            el email de Empresa a setear
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String getSoporteNombre() {
		return soporteNombre;
	}

	public void setSoporteNombre(String soporteNombre) {
		this.soporteNombre = soporteNombre;
	}

	public String getSoporteTelefono() {
		return soporteTelefono;
	}

	public void setSoporteTelefono(String soporteTelefono) {
		this.soporteTelefono = soporteTelefono;
	}

	public String getSoporteEmail() {
		return soporteEmail;
	}

	public void setSoporteEmail(String soporteEmail) {
		this.soporteEmail = soporteEmail;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

}
