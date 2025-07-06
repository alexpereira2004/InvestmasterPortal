package br.com.lunacom.portal.domain.dto.googlesheets;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarteiraDto {
    private String header;
    private String codigoAtivo;
    private String estrategia;
    private BigDecimal precoPago;
    private BigDecimal precoAtual;
    private BigDecimal valorizacao;
    private Integer quantidade;
    private BigDecimal totalInvestido;
    private BigDecimal atualizacao;
    private BigDecimal resultado;
    private LocalDate dataCompra;
}
