/**
 * 
 */
package py.com.hornero.model.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "alias", "empresa" }))
public class Funcionario extends EntidadBase {

	@NotNull
	@Column(name = "nombre")
	private String nombre;
	
	@NotNull
	@Column(name = "apellido")
	private String apellido;
	
	@NotNull
	@Column(name = "contraseña")
	private String contraseña;	
	
	@Column(name = "documento")
	private String documento;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "telefono")
	private String telefono;

	@Column(name = "fecha_nacimiento")
	private Timestamp fechaNacimiento;

	@Column(name ="sueldo_hora")
	private String sueldoHora;
	
	@Column(name ="id_imagen")
	private String idImagen;
	
	@Column(name ="cargo")
	private String cargo;
	
	@ManyToOne
	@JoinColumn(name = "rol")
	private Rol rol;
	
	public Funcionario() {

	}

	/**
	 * @param id
	 *            el id de Usuario
	 */
	public Funcionario(Long id) {
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Timestamp getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Timestamp fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getSueldoHora() {
		return sueldoHora;
	}

	public void setSueldoHora(String sueldoHora) {
		this.sueldoHora = sueldoHora;
	}

	public String getIdImagen() {
		return idImagen;
	}

	public void setIdImagen(String idImagen) {
		this.idImagen = idImagen;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}


}