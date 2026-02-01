package br.com.lunacom.portal.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegraCompraPorHistoricoVendaResponse {
    private String id;
    private String nome;
    private String idMovimentoVenda;
    private String tipo;
    private String status;
    private String periodo;
    private String excluirPrejuizos;
    private String recomendacao;
    private String recomendacaoEscala;
    private String analise;
    private String observacao;
    private String idMonitor;
    private String cotacaoAlvo;
    private String cotacaoAtual;
}
