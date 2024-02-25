package br.com.lunacom.portal.resource.v2.specification;

import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.request.GenericRequest;
import br.com.lunacom.portal.util.DataUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Component
public class AtivoSpecification extends GenericSpecification
        implements Specification<Ativo> {

    @Override
    public void setRequest(GenericRequest request) {

    }

    @Override
    public void setDataUtil(DataUtil dataUtil) {

    }


    @Override
    public Specification<Ativo> and(Specification<Ativo> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Ativo> or(Specification<Ativo> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Ativo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
