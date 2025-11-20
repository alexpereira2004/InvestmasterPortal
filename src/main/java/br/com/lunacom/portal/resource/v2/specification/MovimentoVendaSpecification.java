package br.com.lunacom.portal.resource.v2.specification;

import br.com.lunacom.portal.domain.MovimentoVenda;
import br.com.lunacom.portal.domain.request.MovimentoVendaRequest;
import br.com.lunacom.portal.util.DataUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
public class MovimentoVendaSpecification implements Specification<MovimentoVenda> {

    DataUtil dataUtil;
    MovimentoVendaRequest request;

    @Override
    public Predicate toPredicate(Root<MovimentoVenda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

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

        if (Objects.nonNull(request.getDataVendaInicio())) {
            final LocalDate dataInicio = dataUtil.dataBrParaLocalDate(request.getDataVendaInicio());
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("dataVenda"), dataInicio))
            );
        }

        if (Objects.nonNull(request.getDataVendaFinal())) {
            final LocalDate dataFinal = dataUtil.dataBrParaLocalDate(request.getDataVendaFinal());
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("dataVenda"), dataFinal))
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
