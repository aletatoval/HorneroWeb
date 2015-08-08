/**
 * 
 */
package py.com.mensajeria.model.ejb;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import py.com.mensajeria.model.entity.Localizacion;
import py.com.mensajeria.model.entity.RolPermiso;

/**
 * @author Osmar Olmedo
 * 
 */
@Local
public interface LocalizacionManager extends BaseManager<Localizacion, Long> {
	 public List<Map<String, Object>> listUltimaLocalizacion(Localizacion ejemplo,
             String[] atributos);
	 public List<java.util.Map<String, java.lang.Object>> listAtributosPorFecha(Localizacion ejemplo, String[] atributos, 
			 String[] atributosFecha, Timestamp fechaInicio, Timestamp fechaFin);        
}
