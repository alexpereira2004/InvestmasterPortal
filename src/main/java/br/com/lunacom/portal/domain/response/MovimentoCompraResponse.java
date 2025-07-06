package br.com.lunacom.portal.domain.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class MovimentoCompraResponse extends GenericResponse {
    private Integer id;
    private LocalDate dataAquisicao;
    private BigDecimal precoPago;
    private Integer quantidade;
    private BigDecimal totalInvestido;
    private String indicacao;
    private String estrategia;
    private String ativoNome;
    private String ativoCodigo;
}
