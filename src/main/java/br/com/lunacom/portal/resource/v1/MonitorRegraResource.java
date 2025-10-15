package br.com.lunacom.portal.resource.v1;

import br.com.lunacom.portal.domain.dto.monitor.MonitorRegraDto;
import br.com.lunacom.portal.service.MonitorRegraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value="v1/monitor/regras")
public class MonitorRegraResource {

    private final MonitorRegraService service;

    @GetMapping("/{id}")
    public ResponseEntity<MonitorRegraDto> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}
