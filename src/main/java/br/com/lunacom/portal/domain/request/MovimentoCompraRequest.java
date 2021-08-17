package br.com.lunacom.portal.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Component
public class MovimentoCompraRequest extends GenericRequest {
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








