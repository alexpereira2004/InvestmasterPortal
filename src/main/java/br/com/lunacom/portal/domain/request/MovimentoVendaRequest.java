package br.com.lunacom.portal.domain.request;

import br.com.lunacom.portal.validation.DataBrasil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MovimentoVendaRequest {
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
    @NotBlank(message = "Data do Venda não pode estar vazia (dataVenda)")
    @DataBrasil
    private String dataVenda;
    private String dataVendaInicio;
    private String dataVendaFinal;
    @NotBlank(message = "Informe o Preço Venda (precoVenda)")
    private String precoVenda;
    private String totalFinal;
    private String rendimento;
    @NotBlank(message = "Informe Código do Ativo (ativoVenda)")
    private String ativoCodigo;
}





