package py.com.mensajeria.utils;

public class ExceptionMensajeria extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mensaje;
	private String codigo;
	private String estado;
	private String campo;

//    public ExceptionTangerine(String mensaje) {
//        this.mensaje = mensaje;
//    }
    
    public ExceptionMensajeria(String campo,String codigo, String mensaje) {
    	this.campo = campo;
    	this.codigo = codigo;
    	this.mensaje = mensaje;
    }
    
    public ExceptionMensajeria(String campo,String estado, String codigo, String mensaje) {
    	this.campo = campo;
    	this.codigo = codigo;
    	this.mensaje = mensaje;
    	this.estado = estado;
    }
    
    @Override
    public String getMessage() {
        return this.mensaje;
    }
    
    public String getCodigo() {
        return this.codigo;
    }
    
    public String getEstado() {
        return this.estado;
    }

	public String getCampo() {
		return this.campo;
	}

	
    
}
