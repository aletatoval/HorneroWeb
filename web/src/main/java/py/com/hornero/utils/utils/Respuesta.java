package py.com.hornero.utils.utils;

import java.io.Serializable;

public class Respuesta<T> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private T resultado;
	
	private Boolean error;
	
	private Boolean valido;
	
	private String mensaje;
	        
        private String codigoError;
        
        private String estado;    	
	
	
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

	public T getResultado() {
		return resultado;
	}

	public void setResultado(T resultado) {
		this.resultado = resultado;
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