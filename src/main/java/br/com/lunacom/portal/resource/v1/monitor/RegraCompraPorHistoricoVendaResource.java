package br.com.lunacom.portal.resource.v1.monitor;

import br.com.lunacom.comum.domain.MovimentoCompra;
import br.com.lunacom.comum.domain.entity.monitor.RegraCompraPorHistoricoVenda;
import br.com.lunacom.portal.converter.monitor.RegraCompraPorHistoricoVendaRequestConverter;
import br.com.lunacom.portal.domain.request.MovimentoCompraRequest;
import br.com.lunacom.portal.domain.request.monitor.RegraCompraPorHistoricoVendaRequest;
import br.com.lunacom.portal.domain.response.RegraCompraPorHistoricoVendaResponse;
import br.com.lunacom.portal.service.monitor.RegraCompraPorHistoricoVendaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="v1/monitor/regras/compra-por-historico-venda")
@RestController
public class RegraCompraPorHistoricoVendaResource {

    private final RegraCompraPorHistoricoVendaService service;
    private final RegraCompraPorHistoricoVendaRequestConverter requestConverter;

    @PostMapping
    public ResponseEntity<MovimentoCompra> create(
            @RequestBody @Valid RegraCompraPorHistoricoVendaRequest request) {
        final RegraCompraPorHistoricoVenda entity = requestConverter.encode(request);
        final RegraCompraPorHistoricoVenda response = service.salvar(entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value="/listagem-paginado")
    public ResponseEntity<Page<MovimentoCompraResponse>> pesquisar(MovimentoCompraRequest request, Pageable pageable) {
        service.pesquisarComPaginacao(request, pageable);
//        final Page<MovimentoCompraResponse> response = movimentoCompraPage.map(e -> responseConverter.decode(e));
//        return ResponseEntity.ok(response);
        return ResponseEntity.ok(null);
    }
}
