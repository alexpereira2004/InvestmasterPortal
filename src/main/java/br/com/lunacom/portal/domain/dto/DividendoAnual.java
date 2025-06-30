package br.com.lunacom.portal.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class DividendoAnual {
    private Integer ano;
    private BigDecimal valor;
    private Integer quantidade;
    private BigDecimal dividendYeld;
}
