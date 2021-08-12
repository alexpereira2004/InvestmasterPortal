package br.com.lunacom.portal.resource.v1.specification;

import br.com.lunacom.portal.domain.MovimentoCompra;
import br.com.lunacom.portal.domain.request.MovimentoCompraRequest;
import br.com.lunacom.portal.util.DataUtil;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
public class MovimentoCompraSpecification implements Specification<MovimentoCompra> {

    DataUtil dataUtil;
    MovimentoCompraRequest request;

    @Override
    public Predicate toPredicate(Root<MovimentoCompra> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getId())) {
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), request.getId()))
            );
        }

        if (Objects.nonNull(request.getDataAquisicaoInicio())) {
            final LocalDate dataInicio = dataUtil.dataBrParaLocalDate(request.getDataAquisicaoInicio());
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("dataAquisicao"), dataInicio))
            );
        }

        if (Objects.nonNull(request.getDataAquisicaoFinal())) {
            final LocalDate dataFinal = dataUtil.dataBrParaLocalDate(request.getDataAquisicaoFinal());
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("dataAquisicao"), dataFinal))
            );
        }

        if (Objects.nonNull(request.getIndicacao())) {
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("indicacao"), request.getIndicacao()))
            );
        }

        if (Objects.nonNull(request.getEstrategia())) {
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("estrategia"), request.getEstrategia()))
            );
        }

        if (Objects.nonNull(request.getAtivoCodigo())) {
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("ativo").get("codigo"), request.getAtivoCodigo()))
            );
        }

        return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
    }
}
