package py.com.hornero.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class LogApp implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "CODIGO_ERROR", nullable = true, length = 32)
    private String codigoError;
    
    @Column(name = "ESTADO", nullable = false, length = 64)
    private String estado;
    
    @Column(name = "OPERACION", nullable = false, length = 64)
    private String operacion;    

    @Column(name = "FECHA_ACCION", nullable = false)
    private Timestamp fechaAccion;

    
    @Column(name = "MENSAJE", length = 512, nullable = false)
    private String mensaje;
    

    @ManyToOne(optional = true)
    @JoinColumn(name="FUNCIONARIO", referencedColumnName="id")
    private Funcionario funcionario;

    public Long getPkLogApp() {
        return id;
    }

    public void setPkLogApp(Long id) {
        this.id = id;
    }

    
    /**
     * @return the id
     */
    public Long getId() {
    
        return id;
    }

    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
    
        this.id = id;
    }

    
    /**
     * @return the codigoError
     */
    public String getCodigoError() {
    
        return codigoError;
    }

    
    /**
     * @param codigoError the codigoError to set
     */
    public void setCodigoError(String codigoError) {
    
        this.codigoError = codigoError;
    }

    
    /**
     * @return the estado
     */
    public String getEstado() {
    
        return estado;
    }

    
    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
    
        this.estado = estado;
    }

    
    /**
     * @return the operacion
     */
    public String getOperacion() {
    
        return operacion;
    }

    
    /**
     * @param operacion the operacion to set
     */
    public void setOperacion(String operacion) {
    
        this.operacion = operacion;
    }

    
    /**
     * @return the fechaAccion
     */
    public Timestamp getFechaAccion() {
    
        return fechaAccion;
    }

    
    /**
     * @param fechaAccion the fechaAccion to set
     */
    public void setFechaAccion(Timestamp fechaAccion) {
    
        this.fechaAccion = fechaAccion;
    }

    

    
    /**
     * @return the mensaje
     */
    public String getMensaje() {
    
        return mensaje;
    }

    
    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
    
        this.mensaje = mensaje;
    }

    

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}


    
    
}
