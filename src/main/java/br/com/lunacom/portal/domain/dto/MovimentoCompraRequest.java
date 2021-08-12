package br.com.lunacom.portal.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovimentoCompraRequest {
    private String id;
    private String dataAquisicao;
    private String precoPago;
    private String quantidade;
    private String totalInvestido;
    private String indicacao;
    private String estrategia;
    private String ativoCodigo;
}








