/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.hornero.model.ejb;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericDao<T, ID extends Serializable> {

	public T get(ID id);
	
	public Map<String, Object> getLike(T ejemplo, String[] atributos);

	public T getLocked(ID id);

	public T get(T ejemplo);
	
	public T get(T ejemplo, boolean like, boolean caseSensitive);
	
	public Map<String, Object> getAtributos(T ejemplo, String[] atributos);

	public Map<String, Object> getAtributos(T ejemplo, String[] atributos, boolean like, boolean caseSensitive);

	public void save(T entity) throws Exception;

	public void update(T entity) throws Exception;

	public void delete(ID id) throws Exception;

	public void delete(T entity) throws Exception;

	public Integer total();

	public Integer total(T ejemplo);

	public Integer total(T ejemplo, boolean like);
	
	public Integer total(T ejemplo, boolean like, boolean caseSensitive);
	
	public Integer total(T ejemplo, boolean like, boolean caseSensitive, String propiedadFiltroComunes, String comun);

	public Integer total(T ejemplo, boolean like, boolean caseSensitive, 
				String propiedadFiltroComunes, String comun, String campoComparacion, List<Long> valoresComparacion, String tipoFiltro);	
	
	public Integer total(T ejemplo, boolean like, boolean caseSensitive, 
				List<String> atrMayIgual, List<Object> objMayIgual, List<String> atrMenIgual, List<Object> objMenIgual);	
	
	public Integer total(T ejemplo, boolean like, boolean caseSensitive, 
			String propiedadFiltroComunes, String comun, String campoComparacion, List<Long> valoresComparacion, String tipoFiltro,
			List<String> atrMayIgual, List<Object> objMayIgual, List<String> atrMenIgual, List<Object> objMenIgual);	
	
	public Integer total(T ejemplo, boolean like, boolean caseSensitive, 
		String propiedadFiltroComunes, String comun, String campoComparacion, List<Long> valoresComparacion, String tipoFiltro,
		List<String> atrMayIgual, List<Object> objMayIgual, List<String> atrMenIgual, List<Object> objMenIgual, String camposDistintos, boolean distintos);	

	public List<T> list();

	public List<T> list(Integer primerResultado, Integer cantResultado);

	public List<T> list(T ejemplo);

	public List<T> list(T ejemplo, boolean like);

	public List<T> list(T ejemplo, String orderBy, String dir);

	public List<T> list(T ejemplo, String orderBy, String dir, boolean like);

	public List<T> list(T ejemplo, String[] orderBy, String[] dir);

	public List<T> list(T ejemplo, String[] orderBy, String[] dir, boolean like);

	public List<T> list(T ejemplo, Integer primerResultado,
			Integer cantResultados);

	public List<T> list(T ejemplo, Integer primerResultado,
			Integer cantResultados, boolean like);
	
	public List<T> list(T ejemplo, boolean like, boolean caseSensitive);	
	
	public List<T> list(T ejemplo, Integer primerResultado,
			Integer cantResultados, boolean like, boolean caseSensitive);

	public List<T> list(T ejemplo, Integer primerResultado,
			Integer cantResultados, String orderBy, String dir);

	public List<T> list(T ejemplo, Integer primerResultado,
			Integer cantResultados, String orderBy, String dir, boolean like);

	public List<T> list(T ejemplo, Integer primerResultado,
			Integer cantResultados, String[] orderBy, String[] dir);

	public List<T> list(T ejemplo, Integer primerResultado,
			Integer cantResultados, String[] orderBy, String[] dir, boolean like);

	public List<T> list(T ejemplo, boolean all, Integer primerResultado,
			Integer cantResultados, String[] orderByAttrList,
			String[] orderByDirList, boolean like);
	
	public List<T> list(T ejemplo, boolean all, Integer primerResultado,
			Integer cantResultados, String[] orderByAttrList,
			String[] orderByDirList, boolean like, String campoComparacion, List<Long> valoresComparacion, String tipoFiltro );	
	
	public List<T> list(T ejemplo, boolean all, Integer primerResultado,
			Integer cantResultados, String[] orderByAttrList,
			String[] orderByDirList, boolean like, String campoComparacion, List<Long> valoresComparacion, String tipoFiltro,
			List<String> atrMayIgual, List<Object> objMayIgual, List<String> atrMenIgual, List<Object> objMenIgual);
	
	public List<T> list(T ejemplo, boolean all, Integer primerResultado,
			Integer cantResultados, String[] orderByAttrList,
			String[] orderByDirList, boolean like, String campoComparacion, List<Long> valoresComparacion, String tipoFiltro,
			List<String> atrMayIgual, List<Object> objMayIgual, List<String> atrMenIgual, List<Object> objMenIgual,
			String camposDistintos, boolean distintos);	
	
	public List<T> list(T ejemplo, boolean all, Integer primerResultado,
			Integer cantResultados, String[] orderByAttrList,
			String[] orderByDirList, boolean like, boolean caseSensitive, String campoComparacion, List<Long> valoresComparacion, String tipoFiltro,
			List<String> atrMayIgual, List<Object> objMayIgual, List<String> atrMenIgual, List<Object> objMenIgual,
			String camposDistintos, boolean distintos);	

	public List<Map<String, Object>> listAtributos(T ejemplo, String[] atributos);

	public List<Map<String, Object>> listAtributos(T ejemplo,
			String[] atributos, boolean like);

	public List<Map<String, Object>> listAtributos(T ejemplo,
			String[] atributos, Integer primerResultado, Integer cantResultados);
	
	public List<Map<String, Object>> listAtributos(T ejemplo,
			String[] atributos, Integer primerResultado, Integer cantResultados,
			boolean like, boolean caseSensitive);	

	public List<Map<String, Object>> listAtributos(T ejemplo,
			String[] atributos, Integer primerResultado,
			Integer cantResultados, boolean like);

	public List<Map<String, Object>> listAtributos(T ejemplo,
			String[] atributos, String orderBy, String dir);

	public List<Map<String, Object>> listAtributos(T ejemplo,
			String[] atributos, String orderBy, String dir, boolean like);

	public List<Map<String, Object>> listAtributos(T ejemplo,
			String[] atributos, String[] orderBy, String[] dir);

	public List<Map<String, Object>> listAtributos(T ejemplo,
			String[] atributos, String[] orderBy, String[] dir, boolean like);

	public List<Map<String, Object>> listAtributos(T ejemplo,
			String[] atributos, Integer primerResultado,
			Integer cantResultados, String orderBy, String dir);

	public List<Map<String, Object>> listAtributos(T ejemplo,
			String[] atributos, Integer primerResultado,
			Integer cantResultados, String orderBy, String dir, boolean like);

	public List<Map<String, Object>> listAtributos(T ejemplo,
			String[] atributos, Integer primerResultado,
			Integer cantResultados, String[] orderBy, String[] dir);

	public List<Map<String, Object>> listAtributos(T ejemplo,
			String[] atributos, Integer primerResultado,
			Integer cantResultados, String[] orderBy, String[] dir, boolean like);

	public List<Map<String, Object>> listAtributos(T ejemplo,
			String[] atributos, boolean all, Integer primerResultado,
			Integer cantResultados, String[] orderByAttrList,
			String[] orderByDirList, boolean like, boolean caseSensitive,
			String propiedadFiltroComunes, String comun);
	
	public List<Map<String, Object>> listAtributos(T ejemplo,
			String[] atributos, boolean all, Integer primerResultado,
			Integer cantResultados, String[] orderByAttrList,
			String[] orderByDirList, boolean like, boolean caseSensitive,
			String campoComparacion, List<Long> valoresComparacion, String tipoFiltro );	
	
	public List<Map<String, Object>> listAtributos(T ejemplo,
			String[] atributos, boolean all, Integer primerResultado,
			Integer cantResultados, String[] orderByAttrList,
			String[] orderByDirList, boolean like, boolean caseSensitive,
			String propiedadFiltroComunes, String comun, String campoComparacion, List<Long> valoresComparacion, String tipoFiltro );	
	
	public List<Map<String, Object>> listAtributos(T ejemplo, String[] atributos, 
			String[] orderByAttrList, String[] orderByDirList, boolean like, boolean caseSensitive,
			List<String> atrMayIgual, List<Object> objMayIgual,List<String> atrMenIgual, List<Object> objMenIgual);	
	
	public List<Map<String, Object>> listAtributos(T ejemplo, String[] atributos, 
		 boolean all, Integer primerResultado, Integer cantResultados,
		String[] orderByAttrList, String[] orderByDirList, boolean like, boolean caseSensitive,
		List<String> atrMayIgual, List<Object> objMayIgual,List<String> atrMenIgual, List<Object> objMenIgual);		
	
	public List<Map<String, Object>> listAtributos(T ejemplo,
			String[] atributos, boolean all, Integer primerResultado,
			Integer cantResultados, String[] orderByAttrList,
			String[] orderByDirList, boolean like, boolean caseSensitive,
			String propiedadFiltroComunes, String comun, String campoComparacion,List<Long> valoresComparacion, String tipoFiltro,
			List<String> atrMayIgual, List<Object> objMayIgual,List<String> atrMenIgual, List<Object> objMenIgual);	
	
	public List<Map<String, Object>> listAtributos(T ejemplo,
		String[] atributos, boolean all, Integer primerResultado,
		Integer cantResultados, String[] orderByAttrList,
		String[] orderByDirList, boolean like, boolean caseSensitive,
		String propiedadFiltroComunes, String comun, String campoComparacion,List<Long> valoresComparacion, String tipoFiltro,
		List<String> atrMayIgual, List<Object> objMayIgual,List<String> atrMenIgual, List<Object> objMenIgual,
		String camposDinamicos, boolean distintos);	

}
