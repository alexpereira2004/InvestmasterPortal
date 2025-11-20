package br.com.lunacom.portal.service.monitor;

import br.com.lunacom.comum.domain.entity.monitor.RegraCompraPorHistoricoVenda;
import br.com.lunacom.comum.domain.enumeration.Status;
import br.com.lunacom.comum.domain.enumeration.TipoMovimento;
import br.com.lunacom.portal.repository.monitor.RegraCompraPorHistoricoVendaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegraCompraPorHistoricoVendaService {
    private final RegraCompraPorHistoricoVendaRepository repository;

    public RegraCompraPorHistoricoVenda salvar(RegraCompraPorHistoricoVenda entity) {
        repository.inativarRegrasPorMonitor(entity.getMonitor().getId());
        entity.setStatus(Status.ATIVO);
        entity.setTipo(TipoMovimento.COMPRA);
        return repository.save(entity);
    }
}
