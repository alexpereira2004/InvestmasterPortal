package br.com.lunacom.portal.resource.v1.specification;

import br.com.lunacom.portal.domain.MovimentoCompra;
import br.com.lunacom.portal.domain.request.MovimentoCompraRequest;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
public class MovimentoCompraSpecification implements Specification<MovimentoCompra> {

    MovimentoCompraRequest request;

    @Override
    public Predicate toPredicate(Root<MovimentoCompra> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getId())) {
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), request.getId()))
            );
        }

//        id;
//        dataAquisicao;
//        precoPago;
//        quantidade;
//        totalInvestido;
//        indicacao;
//        estrategia;
//        ativoCodigo;
        return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
    }
}
