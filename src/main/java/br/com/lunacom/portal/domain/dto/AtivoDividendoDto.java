package br.com.lunacom.portal.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class AtivoDividendoDto {
    private String codigo;
    private BigDecimal valorTotal;
    private Integer quantidadeMaxima;
    private String tipo;
    private LocalDate primeiroDividendo;
    private LocalDate ultimoDividendo;
}
