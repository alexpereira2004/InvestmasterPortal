package br.com.lunacom.portal.domain.request;

import br.com.lunacom.portal.validation.DataBrasil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Component
public class MovimentoCompraRequest extends GenericRequest {
    private String id;

    @NotBlank(message = "Data do Aquisição não pode estar vazia (dataAquisicao)")
    @DataBrasil
    private String dataAquisicao;
    private String dataAquisicaoInicio;
    private String dataAquisicaoFinal;
    @NotBlank(message = "Informe o Preço Pago (precoPago)")
    private String precoPago;
    @NotBlank(message = "Informe a quantidade (quantidade)")
    private String quantidade;
    private String totalInvestido;
    private String indicacao;
    private String estrategia;
    private String ativoCodigo;
}








