package br.com.lunacom.portal.domain.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MovimentoCompraResponse {
    private Integer id;
    private LocalDate dataAquisicao;
    private Double precoPago;
    private Integer quantidade;
    private Double totalInvestido;
    private String indicacao;
    private String estrategia;
    private String ativoNome;
    private String ativoCodigo;
}
