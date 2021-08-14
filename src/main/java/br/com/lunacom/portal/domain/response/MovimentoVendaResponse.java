package br.com.lunacom.portal.domain.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MovimentoVendaResponse {
    private Integer id;

    private LocalDate dataAquisicao;
    private Double precoPago;
    private Integer quantidade;
    private Double totalInvestido;

    private LocalDate dataVenda;
    private Double precoVenda;

    private Double totalFinal;
    private Double rendimento;

    private String ativoNome;
    private String ativoCodigo;
}
