package br.com.lunacom.portal.service.monitor;

import br.com.lunacom.portal.domain.dto.monitor.MonitorDto;
import br.com.lunacom.portal.domain.entity.monitor.Monitor;
import br.com.lunacom.portal.repository.monitor.MonitorRegraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static br.com.lunacom.portal.util.MonitorConstants.MONITOR_NAO_ENCONTRADO;

@Slf4j
@RequiredArgsConstructor
@Service
public class MonitorService {

    private final MonitorRegraRepository repository;

    private final List<MonitorRegraInterface> regras;

    public Monitor buscarPorMonitorId(Integer id) {
        Monitor monitor = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MONITOR_NAO_ENCONTRADO));
        return monitor;
    }

    @Transactional(readOnly = true)
    public MonitorDto buscarPorId(Integer id) {
        Monitor monitor = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MONITOR_NAO_ENCONTRADO));
        MonitorDto dto = new MonitorDto(monitor);

        for (MonitorRegraInterface r : regras) {
            var lista = r.buscarPorRegra(monitor);
            if (!lista.isEmpty()) {
                dto.adicionarResultado(r.getTipo(), (List<Object>) lista);
                log.info("→ " + r.getClass().getSimpleName() + " retornou " + lista.size() + " registros");
            }
        }
        return dto;
    }
}
