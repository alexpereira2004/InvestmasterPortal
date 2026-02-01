package br.com.lunacom.portal.converter.monitor;

import br.com.lunacom.comum.domain.entity.monitor.RegraCompraPorHistoricoVenda;
import br.com.lunacom.portal.converter.OneWayConverter;
import br.com.lunacom.portal.domain.response.RegraCompraPorHistoricoVendaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RegraCompraPorHistoricoVendaResponseConverter implements OneWayConverter
        <RegraCompraPorHistoricoVenda, RegraCompraPorHistoricoVendaResponse> {

    @Override
    public RegraCompraPorHistoricoVendaResponse encode(RegraCompraPorHistoricoVenda input) {
        final RegraCompraPorHistoricoVendaResponse item = RegraCompraPorHistoricoVendaResponse
                .builder()
                .id(String.valueOf(input.getId()))
                .nome(input.getMonitor().getAtivo().getCodigo())
                .tipo(input.getTipo().getCodigo())
                .status(input.getStatus().getCodigo())
                .periodo(input.getPeriodo().getCodigo())
                .excluirPrejuizos(input.getExcluirPrejuizos())
                .recomendacao(input.getRecomendacao())
                .recomendacaoEscala(String.valueOf(input.getRecomendacaoEscala()))
                .analise(input.getAnalise())
                .observacao(input.getObservacao())
                .idMonitor(input.getMonitor().getId().toString())
                .build();
        return item;
    }
}
