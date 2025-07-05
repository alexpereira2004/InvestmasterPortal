package br.com.lunacom.portal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class CotacaoAgoraDto {
    private String codigo;
    private String nome;
    private BigDecimal cotacaoAtual;
    private BigDecimal variacaoPeriodo;
    private BigDecimal dividendosAnoAnterior;
    private BigDecimal dyPercentual;
    private LocalDate dataCotacao;
    private LocalDate dataImportacao;
}
