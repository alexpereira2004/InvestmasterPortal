package br.com.lunacom.portal.resource.v1.specification;

import br.com.lunacom.comum.domain.entity.monitor.RegraCompraPorHistoricoVenda;
import br.com.lunacom.portal.domain.request.monitor.RegraCompraPorHistoricoVendaConsultaRequest;
import br.com.lunacom.portal.util.DataUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
public class RegraCompraPorHistoricoVendaSpecification implements Specification<RegraCompraPorHistoricoVenda> {

    DataUtil dataUtil;
    RegraCompraPorHistoricoVendaConsultaRequest request;

    @Override
    public Predicate toPredicate(
            Root<RegraCompraPorHistoricoVenda> root,
            CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getStatus())) {
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), request.getStatus()))
            );
        }

        return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
    }
}
