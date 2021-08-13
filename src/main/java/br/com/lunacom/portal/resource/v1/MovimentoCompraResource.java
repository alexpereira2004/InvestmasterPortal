package br.com.lunacom.portal.resource.v1;

import br.com.lunacom.portal.converter.MovimentoCompraRequestConverter;
import br.com.lunacom.portal.converter.MovimentoCompraResponseConverter;
import br.com.lunacom.portal.domain.MovimentoCompra;
import br.com.lunacom.portal.domain.request.MovimentoCompraRequest;
import br.com.lunacom.portal.domain.response.MovimentoCompraResponse;
import br.com.lunacom.portal.service.MovimentoCompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/v1/movimento-compra")
@Validated
public class MovimentoCompraResource {

    private final MovimentoCompraService service;
    private final MovimentoCompraRequestConverter requestConverter;
    private final MovimentoCompraResponseConverter responseConverter;

    @PostMapping
    public ResponseEntity<MovimentoCompra> create(@RequestBody @Valid MovimentoCompraRequest request) {
        final MovimentoCompra movimento = service.salvar(requestConverter.encode(request));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(movimento.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value="/listagem-paginado")
    public ResponseEntity<Page<MovimentoCompraResponse>> pesquisar(MovimentoCompraRequest request, Pageable pageable) {
        final Page<MovimentoCompra> movimentoCompraPage = service.pesquisarComPaginacao(request, pageable);
        final Page<MovimentoCompraResponse> response = movimentoCompraPage.map(e -> responseConverter.decode(e));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") List<Integer> input) {
        service.removerTodos(input);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/importa-texto-csv")
    public ResponseEntity<Void> createFromCsvText(@RequestBody @Valid String request) {
        service.salvarCsv(request);
        return ResponseEntity.ok().build();
    }
}
