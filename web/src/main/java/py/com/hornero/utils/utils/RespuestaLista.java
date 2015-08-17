package py.com.hornero.utils.utils;

import java.io.Serializable;
import java.util.List;


public class RespuestaLista<T> implements Serializable {
    
    private static final long serialVersionUID = 5653610817878260325L;
    
    private Boolean error;
    
    private Boolean valido;
    
    private String mensaje;
    
    private long totalRegistros;
    
    private String fechaUltimaDescarga;
    
    private List<T> resultado;
        
    private String codigoError;
    
    private String estado;    
    
    
    public long getTotalRegistros() {
    
	return totalRegistros;
    }
    
    public void setTotalRegistros(long cantidadDeRegistros) {
    
	this.totalRegistros = cantidadDeRegistros;
    }
    
    public List<T> getResultado() {
    
	return resultado;
    }
    
    public void setResultado(List<T> lista) {
    
	this.resultado = lista;
    }
    
    public Boolean getError() {
    
	return error;
    }
    
    public void setError(Boolean error) {
    
	this.error = error;
    }
    
    
    public Boolean getValido() {
    
        return valido;
    }

    
    public void setValido(Boolean valido) {
    
        this.valido = valido;
    }

    public String getMensaje() {
    
	return mensaje;
    }
    
    public void setMensaje(String mensaje) {
    
	this.mensaje = mensaje;
    }
    
    public String getFechaUltimaDescarga() {
    
	return fechaUltimaDescarga;
    }
    
    public void setFechaUltimaDescarga(String fechaUltimaDescarga) {
    
	this.fechaUltimaDescarga = fechaUltimaDescarga;
    }

    
    public String getCodigoError() {
    
        return codigoError;
    }

    
    public void setCodigoError(String codigoError) {
    
        this.codigoError = codigoError;
    }

    
    public String getEstado() {
    
        return estado;
    }

    
    public void setEstado(String estado) {
    
        this.estado = estado;
    }
    
    
    
}