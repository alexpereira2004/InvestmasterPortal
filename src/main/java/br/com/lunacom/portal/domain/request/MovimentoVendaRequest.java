package br.com.lunacom.portal.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MovimentoVendaRequest {
    private String id;
    private String dataAquisicao;
    private String dataAquisicaoInicio;
    private String dataAquisicaoFinal;
    private String precoPago;
    private String quantidade;
    private String totalInvestido;
    private String dataVenda;
    private String dataVendaInicio;
    private String dataVendaFinal;
    private String precoVenda;
    private String totalFinal;
    private String rendimento;
    private String ativoCodigo;
}





