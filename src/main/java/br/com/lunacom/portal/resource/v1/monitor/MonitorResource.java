package br.com.lunacom.portal.resource.v1.monitor;

import br.com.lunacom.comum.domain.dto.monitor.MonitorDto;
import br.com.lunacom.portal.service.monitor.MonitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="v1/monitor")
@RestController("monitorRegraV1ResourceV1")
public class MonitorResource {

    private final MonitorService service;

    @GetMapping("/{id}")
    public ResponseEntity<MonitorDto> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}
