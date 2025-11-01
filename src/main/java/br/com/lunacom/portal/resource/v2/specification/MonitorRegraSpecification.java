package br.com.lunacom.portal.resource.v2.specification;

import br.com.lunacom.portal.domain.entity.monitor.MonitorRegra;
import br.com.lunacom.portal.domain.request.GenericRequest;
import br.com.lunacom.portal.domain.request.MonitorRegraRequest;
import br.com.lunacom.portal.util.DataUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MonitorRegraSpecification extends GenericSpecification
        implements Specification<MonitorRegra> {

    DataUtil dataUtil;
    MonitorRegraRequest request;

    @Override
    public void setRequest(GenericRequest request) {
        this.request = (MonitorRegraRequest) request;
    }

    @Override
    public void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    @Override
    public Predicate toPredicate(Root<MonitorRegra> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getStatus())) {
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), request.getStatus().getCodigo()))
            );
        }

        return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
    }
}
