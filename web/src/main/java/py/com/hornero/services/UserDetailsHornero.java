package py.com.hornero.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class UserDetailsHornero extends User {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idEmpresa;
	private String nombreEmpresa;
	private Long idUsuario;
	private String nombreCompleto;
	private String nombreRol;

    public UserDetailsHornero(String username, String password,
            Collection<? extends GrantedAuthority> authorities,
            String nombreCompleto, Long pkEmpresa, String nombreEmpresa, 
            Long pkUsuario, String nombreRol) {
        
    	super(username, password, authorities);
        
    	this.idEmpresa = pkEmpresa;
        this.nombreRol = nombreRol;
        this.nombreEmpresa = nombreEmpresa;
        this.idUsuario = pkUsuario;
        this.nombreCompleto = nombreCompleto;
        
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getNombreRol() {
        return nombreRol;
    }
    
    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

	public static UserDetailsHornero getUsuarioAutenticado() {
        return ((UserDetailsHornero) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public boolean tengoPermiso(String modulo, String permiso) {

        String permisoSolicitado = modulo + "." + permiso;
        if (getUsuarioAutenticado().getAuthorities() != null) {
        	for(GrantedAuthority tmp: getUsuarioAutenticado().getAuthorities()){
        		if(tmp.getAuthority().compareTo(permisoSolicitado) == 0){
        			return true;
        		};
        		
        	}
        }
        return false;
    }
    
	
    public String getCampoActivo(String permisos){
    	String[] listaPermisos = permisos.split(",");
    	for (int i = 0; i<listaPermisos.length; i++){
    		for(GrantedAuthority tmp: getUsuarioAutenticado().getAuthorities()){
        		if(tmp.getAuthority().compareTo(listaPermisos[i]) == 0){
        			return null;
        		};
        		
        	}
    	}
    	return "S"; 
    }

    public boolean soloActivos(String permisos){
    	String[] listaPermisos = permisos.split(",");
    	for (int i = 0; i<listaPermisos.length; i++){
    		if (getUsuarioAutenticado().getAuthorities().contains(new SimpleGrantedAuthority(listaPermisos[i]))){
    			return false;
    		}
    	}
    	return true; 
    }    


}
