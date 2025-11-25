package br.com.lunacom.portal.domain.request.monitor;

import br.com.lunacom.comum.domain.enumeration.Boleano;
import br.com.lunacom.comum.domain.enumeration.PeriodoVenda;
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
public class RegraCompraPorHistoricoVendaRequest {

    private PeriodoVenda periodo;

    private Boleano excluirPrejuizos;

    private Integer monitorId;

    private Integer movimentoVendaId;


}


