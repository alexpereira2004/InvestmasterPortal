package br.com.lunacom.portal.service.monitor;

import br.com.lunacom.portal.domain.entity.monitor.MonitorRegra;
import br.com.lunacom.portal.repository.monitor.MonitorRegraCruzamentoMediaRepository;
import br.com.lunacom.portal.util.MonitorConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitorRegraCruzamentoMediaService implements MonitorRegraInterface {

    private final MonitorRegraCruzamentoMediaRepository repository;

    @Override
    public String getTipo() {
        return MonitorConstants.CRUZAMENTO;
    }

    @Override
    public List<?> buscarPorRegra(MonitorRegra regra) {
        return repository.findAllByRegraId(regra.getId());
    }
}
