package br.com.lunacom.portal.service.monitor;

import br.com.lunacom.portal.domain.dto.monitor.MonitorRegraDto;
import br.com.lunacom.portal.domain.entity.monitor.MonitorRegra;
import br.com.lunacom.portal.repository.monitor.MonitorRegraRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static br.com.lunacom.portal.util.MonitorConstants.REGRA_NAO_ENCONTRADA;

@Slf4j
@RequiredArgsConstructor
@Service
public class MonitorRegraService {

    private final MonitorRegraRepository repository;

    private final List<MonitorRegraInterface> regras;

    @Transactional(readOnly = true)
    public MonitorRegraDto buscarPorId(Integer id) {
        MonitorRegra regra = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(REGRA_NAO_ENCONTRADA));
        MonitorRegraDto dto = new MonitorRegraDto(regra);

        for (MonitorRegraInterface r : regras) {
            var lista = r.buscarPorRegra(regra);
            if (!lista.isEmpty()) {
                dto.adicionarResultado(r.getTipo(), (List<Object>) lista);
                log.info("→ " + r.getClass().getSimpleName() + " retornou " + lista.size() + " registros");
            }
        }
        return dto;
    }
}
