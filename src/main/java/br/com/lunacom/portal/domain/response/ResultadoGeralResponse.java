package br.com.lunacom.portal.domain.response;

import br.com.lunacom.portal.domain.dto.DividendoAnual;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoGeralResponse {
    private BigDecimal precoMedio;
    private BigDecimal cotacaoAtual;
    private Integer quantidadeCotas;
    private BigDecimal investimentoTotal;
    private BigDecimal investimentoTotalAtualizado;
    private BigDecimal investimentoTotalAtualizadoComDividendos;
    private BigDecimal proporcaoTotalInvestido;
    private BigDecimal proporcaoTipoAtivoInvestido;
    private BigDecimal resultado;
    private BigDecimal resultadoPercentual;
    private BigDecimal resultadoComDividendo;
    private BigDecimal resultadoComDividendoPercentual;
    private BigDecimal dividendYeld;
    private List<DividendoAnual> dividendos;
}
