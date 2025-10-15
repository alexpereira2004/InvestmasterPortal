package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.dto.monitor.MonitorRegraDto;
import br.com.lunacom.portal.repository.MonitorRegraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class MonitorRegraService {

    private final MonitorRegraRepository repository;

    public MonitorRegraDto buscarPorId(Integer id) {
        return repository.buscarComRegras(id)
                .map(MonitorRegraDto::new)
                .orElseThrow(() -> new EntityNotFoundException("Regra não encontrada: " + id));
    }
}
