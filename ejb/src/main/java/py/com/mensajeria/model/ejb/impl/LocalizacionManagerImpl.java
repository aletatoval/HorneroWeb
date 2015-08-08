/**
 * 
 */
package py.com.mensajeria.model.ejb.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManager;

import py.com.mensajeria.model.ejb.LocalizacionManager;
import py.com.mensajeria.model.entity.Localizacion;
import py.com.mensajeria.model.entity.RolPermiso;

import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.hibernate.HibernateMetadataUtil;
import com.googlecode.genericdao.search.jpa.JPASearchProcessor;


/**
 * @author Osmar Olmedo
 * 
 */
@Stateless
public class LocalizacionManagerImpl extends BaseManagerImpl<Localizacion, Long>  implements
		LocalizacionManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * py.com.personal.visitas.model.ejb.impl.GenericDaoImpl#getEntityBeanType()
	 */
	@Override
	protected Class<Localizacion> getEntityBeanType() {
		return Localizacion.class;
	}
	
	@Override
    public List<Map<String, Object>> listUltimaLocalizacion(Localizacion ejemplo,
                                                            String [] atributos)
    {
        if (atributos == null || atributos.length == 0) {
            throw new RuntimeException("La lista de propiedades no puede ser nula o vacía");
        }
        
        JPASearchProcessor jpaSP = new JPASearchProcessor(HibernateMetadataUtil.getInstanceForSessionFactory(this.getSessionFactory()));
        Search searchConfig = this.getSearchConfig(jpaSP, ejemplo, atributos, false, 0, 1, null, null, false);
        
        // Acá indicamos que solo traiga la última localización tomada.
        searchConfig.addSort("fechaCreacion", true, true);
        
        return jpaSP.search(this.getEm(), searchConfig);
    }
        public Search getSearchConfig(JPASearchProcessor jpaSP, Localizacion ejemplo, String[] atributos, boolean all, Integer primerResultado, Integer cantResultados,
            String[] orderByAttrList, String[] orderByDirList, boolean like) {


        Search searchConfig = new Search(this.getEntityBeanType());

        if (ejemplo != null) {
            ExampleOptions exampleOptions = new ExampleOptions();
            exampleOptions.setExcludeNulls(true);

            if (like) {
                exampleOptions.setIgnoreCase(true);
                exampleOptions.setLikeMode(ExampleOptions.ANYWHERE);
            }

            searchConfig.addFilter(jpaSP.getFilterFromExample(ejemplo, exampleOptions));
        }

        if (!all) {
            searchConfig.setFirstResult(primerResultado);
            searchConfig.setMaxResults(cantResultados);
        }

        if (orderByAttrList != null && orderByDirList != null && orderByAttrList.length == orderByDirList.length) {
            for (int i = 0; i < orderByAttrList.length; i++) {
                if (orderByDirList[i].equalsIgnoreCase("desc")) {
                    searchConfig.addSortDesc(orderByAttrList[i]);
                } else {
                    searchConfig.addSortAsc(orderByAttrList[i]);
                }
            }
        } else if ((orderByAttrList != null && orderByDirList == null) || (orderByAttrList == null && orderByDirList != null)) {
            throw new RuntimeException("No puede proporcionarse una lista de "
                    + "atributos para ordenamiento sin la correspondiente "
                    + "lista de direcciones de ordenamiento, o viceversa");
        } else if (orderByAttrList != null && orderByDirList != null && orderByAttrList.length != orderByDirList.length) {
            throw new RuntimeException("No puede proporcionarse una lista de "
                    + "atributos para ordenamiento de tamaño dieferente a la "
                    + "lista de direcciones de ordenamiento");
        }


        if (atributos != null && atributos.length > 0) {
            for (String a : atributos) {
                searchConfig.addField(a);
            }
            searchConfig.setResultMode(Search.RESULT_MAP);
        }

        return searchConfig;
    }

    public SessionFactory getSessionFactory() {
        if (this.getEm().getDelegate() instanceof HibernateEntityManager) {
            return ((HibernateEntityManager) this.getEm().getDelegate()).getSession().getSessionFactory();
        } else {
            return ((Session) this.getEm().getDelegate()).getSessionFactory();
        }
    }

    @Override
    public List<Map<String, Object>> listAtributosPorFecha(
            Localizacion ejemplo,
            String[] atributos,
            String[] atributosFecha,
            Timestamp fechaInicio,
            Timestamp fechaFin)
    {
        JPASearchProcessor jpaSP = new JPASearchProcessor(
                HibernateMetadataUtil.getInstanceForSessionFactory(this.getSessionFactory()));

        Search searchConfig = new Search(this.getEntityBeanType());

        if (ejemplo != null) {
            ExampleOptions exampleOptions = new ExampleOptions();
            exampleOptions.setExcludeNulls(true);
            searchConfig.addFilter(jpaSP.getFilterFromExample(ejemplo, exampleOptions));
        }

        for (String atrFec : atributosFecha) {
            if (fechaInicio == null) {
                searchConfig.addFilter(Filter.lessOrEqual(atrFec, fechaFin));
            } else {
                searchConfig.addFilter(
                        Filter.and(Filter.greaterOrEqual(atrFec, fechaInicio),
                        Filter.lessOrEqual(atrFec, fechaFin)));
            }
        }
        if (atributos != null && atributos.length > 0) {
            for (String a : atributos) {
                searchConfig.addField(a);
            }
            searchConfig.setResultMode(Search.RESULT_MAP);
        }
        searchConfig.addSort("fechaCreacion", false, true);

        return jpaSP.search(getEm(), searchConfig);
    }
 }
