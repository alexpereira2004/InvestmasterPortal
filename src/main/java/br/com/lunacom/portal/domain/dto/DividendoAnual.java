package br.com.lunacom.portal.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DividendoAnual {
    private Integer ano;
    private BigDecimal valor;
    private Integer quantidade;
    private BigDecimal dividendYeld;
    private BigDecimal dividendYeldAtualizado;

}
