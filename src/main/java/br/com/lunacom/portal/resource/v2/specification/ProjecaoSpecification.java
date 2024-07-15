package br.com.lunacom.portal.resource.v2.specification;

import br.com.lunacom.portal.domain.Projecao;
import br.com.lunacom.portal.domain.request.GenericRequest;
import br.com.lunacom.portal.domain.request.ProjecaoRequest;
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
public class ProjecaoSpecification extends GenericSpecification implements Specification<Projecao> {

    DataUtil dataUtil;
    ProjecaoRequest request;

    @Override
    public void setRequest(GenericRequest request) {
        this.request = (ProjecaoRequest) request;
    }

    @Override
    public void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    @Override
    public org.springframework.data.jpa.domain.Specification<Projecao> and(Specification<Projecao> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Projecao> or(org.springframework.data.jpa.domain.Specification<Projecao> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Projecao> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getAno())) {
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("ano"), request.getAno()))
            );
        }

        if (Objects.nonNull(request.getTotalizador())) {
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("totalizador"), request.getTotalizador()))
            );
        }

        if (Objects.nonNull(request.getTipo())) {
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("tipo"), request.getTipo()))
            );
        }

        return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
    }

}
