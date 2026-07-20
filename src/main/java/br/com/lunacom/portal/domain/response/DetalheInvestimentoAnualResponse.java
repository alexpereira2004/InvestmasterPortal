package br.com.lunacom.portal.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalheInvestimentoAnualResponse extends GenericResponse {
    private Map<Integer, BigDecimal> rendaFixaMensalMap = new HashMap<>();
    private BigDecimal totalRendaFixa;
    private Map<Integer, BigDecimal> aporteProprioMensalMap = new HashMap<>();
    private BigDecimal totalAporteProprio;

}
