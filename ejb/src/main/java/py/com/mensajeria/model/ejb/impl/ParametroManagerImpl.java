/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.mensajeria.model.ejb.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import py.com.mensajeria.model.ejb.ParametroManager;
import py.com.mensajeria.model.entity.Parametro;
import py.com.mensajeria.model.entity.Usuario;

/**
 *
* @author Hermann Bottger
 */
@Stateless
public class ParametroManagerImpl extends GenericDaoImpl<Parametro, Long> implements ParametroManager{

    @Override
    protected Class<Parametro> getEntityBeanType() {
        return Parametro.class;
    }
    
    @Override
    public Usuario getSuperUsuario() throws Exception {
        //Obtiene de la tabla parametros el codigo del superusuario
        Parametro entidad = new Parametro();
        entidad.setNombre("superUsuario");
        Parametro superusuario = this.get(entidad);
        
        //Obtiene de la tabla parametros la clave  del superusuario
        entidad=new Parametro();
        entidad.setNombre("clave");
        Parametro clave = this.get(entidad);
        
        //Setea los datos obtenidos en un objeto Usuario para mejor mapeo de datos  
        Usuario superUsuario=new Usuario();
        superUsuario.setId(new Long(0));
        superUsuario.setAlias(superusuario.getValor());
        superUsuario.setClave(clave.getValor());
      
       return superUsuario;
    }
   
    /**
     * Metodo en donde creamos el objeto geturlCaos de tipo String para ser utilizado por el parametroManager
     * @return
     * @throws Exception 
     */
    @Override
    public String geturlCaos() throws Exception {
        //Obtiene de la tabla parametros el valor de la url de caos
        Parametro entidad = new Parametro();
        entidad.setNombre("personalRootWS");
        Parametro getUrlCaos = this.get(entidad);
        
        if (getUrlCaos != null){
            getUrlCaos.getValor();
            return getUrlCaos.getValor();
        } else {
            return null;
        }
       
    }
    
    /**
     * Medoto en donde creamos el objeto getnombreAplicacion de tipo String para utilizarlo en parametroManager
     * @return
     * @throws Exception 
     */
    @Override
    public String getnombreAplicacion() throws Exception {
        //Obtiene de la tabla parametros el nombre de la aplicacion
        Parametro entidad = new Parametro();
        entidad.setNombre("nombreAplicacion");
        Parametro getnombreApli = this.get(entidad);
        
        if (getnombreApli != null){
            getnombreApli.getValor();
            return getnombreApli.getValor();
        } else {
            return null;
        }
        
    }
    
    @Override
    public Usuario validarSuperUsuario(String nombreUsuario, String pass) throws Exception {
        Usuario usuarioEjemplo = new Usuario();
        usuarioEjemplo.setAlias(nombreUsuario);
        usuarioEjemplo.setClave(pass);
        Usuario userDetails = this.getSuperUsuario();
        if (userDetails != null) {
            if(userDetails.getAlias().equals(nombreUsuario))
            {
                if(userDetails.getClave().equals(pass))
                {
                    return userDetails;
                }
            }

        }
        return null;
    }    

    @Override
    public Map<String, Object> ejecutarSQL(String sql, String tipo) {
            Map<String, Object> retorno = new HashMap<String, Object>();
        try {
           
            List<Object>lista = null;
            Object elemento= null;
            if (tipo.compareToIgnoreCase("lista") == 0){
                lista = getEm().createNativeQuery(sql).getResultList();
                retorno.put("resultado", lista);
            } else if(tipo.compareToIgnoreCase("unico") == 0){
                elemento= getEm().createNativeQuery(sql).executeUpdate();
                retorno.put("resultado", elemento);
            }

            retorno.put("error", false);            
        
        } catch (Exception e) {
            retorno.put("error", true); 
            retorno.put("resultado", null); 
        }

        return retorno;
        
    }
    
    
}
