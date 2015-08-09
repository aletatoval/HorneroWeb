package py.com.hornero.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Hermann Bottger
 */
@Entity
@Table(name = "PARAMETRO")
public class Parametro implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 7642836440322366449L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="NOMBRE", unique = true, nullable=false, length=64)
    private String nombre;

    @Column(name="VALOR", nullable=false, length=64)
    private String valor;
    
    public Parametro() {
    }

    public Parametro(Long id) {
        this.setId(id);
    }    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    
}
