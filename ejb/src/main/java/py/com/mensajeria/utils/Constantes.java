package py.com.mensajeria.utils;

import java.math.MathContext;

public class Constantes {
	
    public static MathContext mc = new MathContext(4);    
    
	
 // ******************             Roles          ***************************************
    
    public static String ROLSUPERVISOR="Supervisor";
    public static String ROLJEFE="Jefe";
    public static String ROLGERENTE="Gerente";
    public static String ROLADMINSTRADOR="Administrador";
    public static String ROLSUPERUSER="SuperUser";
    
    
// ******************             Formatos de fecha             ***************************************
    
    public static final String FORMATOFECHAHORA = "dd/MM/yyyy HH:mm:ss";
    public static final String FORMATOHORA = "HH:mm:ss";
    public static final String FORMATOFECHA = "dd/MM/yyyy";
    public static final String FORMATOFECHADATABASE = "yyyy-MM-dd";    

    
    
 // ******************            Atributos de la Entidad Base       ***************************************
    
    public static String atributosBase= "id,fechaCreacion,idUsuarioCreacion,fechaModificacion,"
                                        + "idUsuarioModificacion,eliminado,origenModif,";
    
    
// ******************             Manejo de Mensajes           ***************************************
    
    public static String MENSAJE_INFO ="alert-info";
    public static String MENSAJE_ERROR ="alert-danger";
    public static String MENSAJE_EXITO  ="alert-success";
    public static String MENSAJE_WARNING   ="alert-warning";    
    
    public static String rootInactivacion = "Desactivación desde Root";
    public static String rootEliminacion = "Eliminación desde Root";
    public static String rootActivacion = "Activación desde Root";
    public static String rootAlta = "Agregar desde Root";
    public static String rootBaja = "Eliminar desde Root";
    public static String rootModificacion = "Modificación desde Root";
        
    public static String inactivacion = "Desactivación";
    public static String eliminacion = "Eliminación";
    public static String activacion = "Activación";
    public static String alta = "Agregar";
    public static String baja = "Eliminar";
    public static String modificacion = "Modificación";
    public static String asociacion = "Asociación";
    public static String login = "Login";
    public static String loginMovil = "Login desde el Móvil";
    
    public static String logout = "Logout";
    public static String generarClave = "GeneracionClave";
    public static String cambioClave = "CambioClave";
    public static String sincronizacion = "Sincronización";
    public static String exitoso = "Exitoso";
    public static String fallido = "Fallido";
    public static String importacion = "Importación";
    public static String exportacion = "Exportación";
    public static String solicitudVersion = "Solicitud de Version";
    public static String solicitudInstalador = "Solicitud de instalador";
    public static String solicitudClave = "Solicitud de Clave";
    public static String validacion = "Validación";

 // ******************             Varios             ***************************************

    public static String guaranies = "Guaraníes";
    public static String OP_OR = "OR";
    public static String OP_AND = "AND";
    
    
}
