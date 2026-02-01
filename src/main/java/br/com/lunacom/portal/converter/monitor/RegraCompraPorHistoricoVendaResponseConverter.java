package br.com.lunacom.portal.converter.monitor;

import br.com.lunacom.comum.domain.entity.monitor.RegraCompraPorHistoricoVenda;
import br.com.lunacom.portal.converter.OneWayConverter;
import br.com.lunacom.portal.domain.response.RegraCompraPorHistoricoVendaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class RegraCompraPorHistoricoVendaResponseConverter implements OneWayConverter
        <RegraCompraPorHistoricoVenda, RegraCompraPorHistoricoVendaResponse> {

    @Override
    public RegraCompraPorHistoricoVendaResponse encode(RegraCompraPorHistoricoVenda input) {
        final RegraCompraPorHistoricoVendaResponse item = RegraCompraPorHistoricoVendaResponse.builder()
                .id(String.valueOf(input.getId()))
                .nome(Optional.ofNullable(input.getMonitor())
                        .map(m -> m.getAtivo())
                        .map(a -> a.getCodigo())
                        .orElse(null))
                .tipo(Optional.ofNullable(input.getTipo())
                        .map(t -> t.getCodigo())
                        .orElse(null))
                .status(Optional.ofNullable(input.getStatus())
                        .map(s -> s.getCodigo())
                        .orElse(null))
                .periodo(Optional.ofNullable(input.getPeriodo())
                        .map(p -> p.getCodigo())
                        .orElse(null))
                .excluirPrejuizos(input.getExcluirPrejuizos())
                .recomendacao(input.getRecomendacao())
                .recomendacaoEscala(String.valueOf(input.getRecomendacaoEscala()))
                .analise(input.getAnalise())
                .observacao(input.getObservacao())
                .idMonitor(Optional.ofNullable(input.getMonitor())
                        .map(m -> m.getId())
                        .map(Object::toString)
                        .orElse(null))
                .build();
        return item;
    }
}
