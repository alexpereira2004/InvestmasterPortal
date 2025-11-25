package br.com.lunacom.portal.service.monitor;

import br.com.lunacom.comum.domain.entity.monitor.Monitor;
import br.com.lunacom.portal.repository.monitor.MonitorRegraVolumeRepository;
import br.com.lunacom.portal.util.MonitorConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegraVolumeService implements MonitorRegraInterface {

    private final MonitorRegraVolumeRepository repository;

    @Override
    public String getTipo() {
        return MonitorConstants.VOLUME;
    }

    @Override
    public List<?> buscarPorRegra(Monitor regra) {
        return repository.findAllByRegraId(regra.getId());
    }
}
