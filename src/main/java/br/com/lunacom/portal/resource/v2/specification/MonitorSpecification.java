package br.com.lunacom.portal.resource.v2.specification;

import br.com.lunacom.portal.domain.entity.monitor.Monitor;
import br.com.lunacom.portal.domain.request.GenericRequest;
import br.com.lunacom.portal.domain.request.MonitorRegraRequest;
import br.com.lunacom.portal.util.DataUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MonitorSpecification extends GenericSpecification
        implements Specification<Monitor> {

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
    public Predicate toPredicate(Root<Monitor> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getAtivoCodigo())) {
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("ativo").get("codigo"), request.getAtivoCodigo()))
            );
        }

        if (Objects.nonNull(request.getStatus())) {
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), request.getStatus().getCodigo()))
            );
        }

        return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
    }
}
