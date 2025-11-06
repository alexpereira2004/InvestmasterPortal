package br.com.lunacom.portal.service.monitor;

import br.com.lunacom.portal.domain.entity.monitor.RegraCompraPorHistoricoVenda;
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
        return repository.save(entity);
    }
}
