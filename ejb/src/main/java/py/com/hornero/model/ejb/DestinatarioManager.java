package py.com.hornero.model.ejb;

import javax.ejb.Local;

import py.com.hornero.model.entity.Destinatario;

@Local
public interface DestinatarioManager extends BaseManager<Destinatario, Long> {

}
