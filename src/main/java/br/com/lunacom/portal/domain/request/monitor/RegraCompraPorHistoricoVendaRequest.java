package br.com.lunacom.portal.domain.request.monitor;

import br.com.lunacom.portal.domain.enumeration.Boleano;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Component
public class RegraCompraPorHistoricoVendaRequest {

    // U: Ultima venda, A: Ano atual, D: Ultimos 12 meses, T: Todo historico
    @Size(max = 1)
    private String periodo;

    /**
     * S: Sim, N: Nao
     */
    private Boleano excluirPrejuizos;

    /**
     * C: Compra, V: Venda, N: Neutro
     */
    @Size(max = 2)
    private String recomendacao;

    @Size(max = 2)
    private Integer recomendacaoEscala;

    private String analise;

    @Size(max = 400)
    private String observacao;

    private Integer monitorId;

    private Integer movimentoVendaId;


}


