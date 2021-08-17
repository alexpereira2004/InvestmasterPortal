package br.com.lunacom.portal.resource.v2.specification;

import br.com.lunacom.portal.domain.Dividendo;
import br.com.lunacom.portal.domain.request.DividendoRequest;
import br.com.lunacom.portal.domain.request.GenericRequest;
import br.com.lunacom.portal.util.DataUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DividendoSpecification extends GenericSpecification 
        implements Specification<Dividendo> {

    DataUtil dataUtil;
    DividendoRequest request;

    @Override
    public void setRequest(GenericRequest request) {
        this.request = (DividendoRequest) request;
    }

    @Override
    public void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    @Override
    public Predicate toPredicate(Root<Dividendo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(request.getId())) {
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), request.getId()))
            );
        }

        if (Objects.nonNull(request.getDataRecebimentoInicio())) {
            final LocalDate dataInicio = dataUtil.dataBrParaLocalDate(request.getDataRecebimentoInicio());
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo(root.get("dataRecebimento"), dataInicio))
            );
        }

        if (Objects.nonNull(request.getDataRecebimentoFinal())) {
            final LocalDate dataFinal = dataUtil.dataBrParaLocalDate(request.getDataRecebimentoFinal());
            predicates.add(
                    criteriaBuilder.and(criteriaBuilder.lessThanOrEqualTo(root.get("dataRecebimento"), dataFinal))
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
