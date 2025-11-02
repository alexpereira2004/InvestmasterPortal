package br.com.lunacom.portal.resource.v1.monitor;

import br.com.lunacom.portal.converter.monitor.RegraCompraPorHistoricoVendaRequestConverter;
import br.com.lunacom.portal.domain.MovimentoCompra;
import br.com.lunacom.portal.domain.entity.monitor.RegraCompraPorHistoricoVenda;
import br.com.lunacom.portal.domain.request.monitor.RegraCompraPorHistoricoVendaRequest;
import br.com.lunacom.portal.service.monitor.RegraCompraPorHistoricoVendaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="v1/monitor/regras/compra-por-historico-venda")
@RestController
public class RegraCompraPorHistoricoVendaResource {

    private final RegraCompraPorHistoricoVendaService service;
    private final RegraCompraPorHistoricoVendaRequestConverter converter;
    @PostMapping
    public ResponseEntity<MovimentoCompra> create(
            @RequestBody @Valid RegraCompraPorHistoricoVendaRequest request) {
        final RegraCompraPorHistoricoVenda entity = converter.encode(request);
        final RegraCompraPorHistoricoVenda response = service.salvar(entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
