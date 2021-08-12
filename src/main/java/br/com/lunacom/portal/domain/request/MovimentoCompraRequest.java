package br.com.lunacom.portal.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MovimentoCompraRequest {
    private String id;
    private String dataAquisicao;
    private String dataAquisicaoInicio;
    private String dataAquisicaoFinal;
    private String precoPago;
    private String quantidade;
    private String totalInvestido;
    private String indicacao;
    private String estrategia;
    private String ativoCodigo;
}








