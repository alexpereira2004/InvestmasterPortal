package br.com.lunacom.portal.resource.v1;

import br.com.lunacom.portal.converter.AtivoResponseConverter;
import br.com.lunacom.portal.converter.MovimentoVendaRequestConverter;
import br.com.lunacom.portal.converter.MovimentoVendaResponseConverter;
import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.MovimentoVenda;
import br.com.lunacom.portal.domain.request.MovimentoVendaRequest;
import br.com.lunacom.portal.domain.response.AtivoResponse;
import br.com.lunacom.portal.domain.response.MovimentoVendaResponse;
import br.com.lunacom.portal.service.MovimentoVendaService;
import br.com.lunacom.portal.util.StringParser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/v1/movimento-venda")
@Validated
public class MovimentoVendaResource {
    private final MovimentoVendaService service;
    private final MovimentoVendaRequestConverter requestConverter;
    private final MovimentoVendaResponseConverter responseConverter;
    private final AtivoResponseConverter ativoResponseConverter;

    @PostMapping
    public ResponseEntity<MovimentoVenda> create(@RequestBody @Valid MovimentoVendaRequest request) {
        final MovimentoVenda movimento = service.salvar(requestConverter.encode(request));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(movimento.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value="/listagem-paginado")
    public ResponseEntity<Page<MovimentoVendaResponse>> pesquisar(MovimentoVendaRequest request, Pageable pageable) {
        final Page<MovimentoVenda> movimentoVendaPage = service.pesquisarComPaginacao(request, pageable);
        final Page<MovimentoVendaResponse> response = movimentoVendaPage.map(e -> responseConverter.decode(e));
        return ResponseEntity.ok(response);
    }

    // @TODO Aplicar cache
    @GetMapping(value="/ativos-com-venda")
    public ResponseEntity<List<AtivoResponse>> buscarTodosAtivosComVenda() {
        final List<Ativo> ativos = service.buscarTodosAtivosComVenda();
        return ResponseEntity.ok(ativoResponseConverter.decode(ativos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") List<Integer> input) {
        service.removerTodos(input);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/importa-texto-csv")
    public ResponseEntity<Void> createFromCsvText(@RequestBody @Valid String request)
            throws UnsupportedEncodingException {
        service.salvarCsv(StringParser.prepareCsvFromRequest(request));
        return ResponseEntity.ok().build();
    }
}
