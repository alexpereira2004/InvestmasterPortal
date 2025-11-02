package br.com.lunacom.portal.converter.monitor;

import br.com.lunacom.portal.converter.OneWayConverter;
import br.com.lunacom.portal.domain.entity.monitor.Monitor;
import br.com.lunacom.portal.domain.entity.monitor.RegraCompraPorHistoricoVenda;
import br.com.lunacom.portal.domain.request.monitor.RegraCompraPorHistoricoVendaRequest;
import br.com.lunacom.portal.service.MovimentoVendaService;
import br.com.lunacom.portal.service.monitor.MonitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class RegraCompraPorHistoricoVendaRequestConverter implements OneWayConverter
        <RegraCompraPorHistoricoVendaRequest, RegraCompraPorHistoricoVenda> {

    private final MonitorService monitorService;

    private final MovimentoVendaService movimentoVendaService;

    @Override
    public RegraCompraPorHistoricoVenda encode(RegraCompraPorHistoricoVendaRequest input) {
        final Monitor monitor = monitorService
                .buscarPorMonitorId(input.getMonitorId());

        final RegraCompraPorHistoricoVenda build = RegraCompraPorHistoricoVenda.builder()
                .periodo(input.getPeriodo().getCodigo())
                .excluirPrejuizos(input.getExcluirPrejuizos().getCodigo())
                .monitor(monitor)
                .build();

        if (Objects.nonNull(input.getMovimentoVendaId())) {
            build.setMovimentoVenda(movimentoVendaService
                    .buscarPorId(input.getMovimentoVendaId()));
        }

        return build;
    }
}
