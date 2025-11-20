package br.com.lunacom.portal.service.monitor;

import br.com.lunacom.comum.domain.entity.monitor.Monitor;
import br.com.lunacom.portal.repository.monitor.MonitorRegraCruzamentoMediaRepository;
import br.com.lunacom.portal.util.MonitorConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegraCruzamentoMediaService implements MonitorRegraInterface {

    private final MonitorRegraCruzamentoMediaRepository repository;

    @Override
    public String getTipo() {
        return MonitorConstants.CRUZAMENTO;
    }

    @Override
    public List<?> buscarPorRegra(Monitor regra) {
        return repository.findAllByRegraId(regra.getId());
    }
}
