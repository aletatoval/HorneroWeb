package py.com.hornero.model.ejb;

import javax.ejb.Local;

import py.com.hornero.model.entity.Empresa;
@Local
public interface EmpresaManager extends BaseManager<Empresa, Long> {

}
