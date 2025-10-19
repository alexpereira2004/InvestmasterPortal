package br.com.lunacom.portal.service.monitor;

import br.com.lunacom.portal.domain.entity.monitor.MonitorRegra;
import br.com.lunacom.portal.repository.monitor.MonitorRegraQuedaPercentualRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.lunacom.portal.util.MonitorConstants.PERCENTUAL;

@Service
@RequiredArgsConstructor
public class MonitorRegraQuedaPercentualService implements MonitorRegraInterface {

    private final MonitorRegraQuedaPercentualRepository repository;


    @Override
    public String getTipo() {
        return PERCENTUAL;
    }

    @Override
    public List<?> buscarPorRegra(MonitorRegra regra) {
        return repository.findAllByRegraId(regra.getId());
    }
}