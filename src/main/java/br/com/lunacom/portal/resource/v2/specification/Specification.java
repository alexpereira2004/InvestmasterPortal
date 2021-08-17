package br.com.lunacom.portal.resource.v2.specification;

import br.com.lunacom.portal.domain.request.GenericRequest;
import br.com.lunacom.portal.util.DataUtil;

public interface Specification {
    void setRequest(GenericRequest request);
    void setDataUtil(DataUtil dataUtil);
}
