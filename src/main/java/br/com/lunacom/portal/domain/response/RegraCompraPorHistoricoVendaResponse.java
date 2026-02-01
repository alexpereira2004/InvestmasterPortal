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
    private String id; //: 15,
    private String nome; //: 'ABCB4',
    private String idMovimentoVenda; //: 122,
    private String tipo; //: TipoMovimento.ENTRADA, // Representado por 'E' no resultset
    private String status; //: Status.ATIVO,        // Representado por 'A'
    private String periodo; //: PeriodoVenda.ULTIMOS_12_MESES, // 'D' (pelo mapeamento Java) ou 'E' conforme o dado bruto
    private String excluirPrejuizos; //: 'N',
    private String recomendacao; //: 'N',
    private String recomendacaoEscala; //: 0,
    private String analise; //: 'O preço atual considerado para o calculo foi de R$ 23.65. O valor da aquisição na única venda foi de R$ 19.07. A relação entre os preços está 24.02% mais cara',
    private String observacao; //: 'O preço atual considerado para o calculo foi de R$ 23.65. O valor da aquisição na única venda foi de R$ 19.07. A relação entre os preços está 24.02% mais cara',
    private String idMonitor; //: 12,
    private String cotacaoAlvo; //: 12.54,
    private String cotacaoAtual; //: 13.60
}
