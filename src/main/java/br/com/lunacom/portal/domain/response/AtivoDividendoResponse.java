package br.com.lunacom.portal.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class AtivoDividendoResponse {
    private String codigo;
    private Double valorTotal;
    private Long quantidadeMaxima;
    private String tipo;
    private LocalDate primeiroDividendo;
    private LocalDate ultimoDividendo;
}
