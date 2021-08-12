package br.com.lunacom.portal.resource;

import br.com.lunacom.portal.converter.MovimentoCompraRequestConverter;
import br.com.lunacom.portal.domain.MovimentoCompra;
import br.com.lunacom.portal.domain.dto.MovimentoCompraRequest;
import br.com.lunacom.portal.service.MovimentoCompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/v1/movimento-compra")
@Validated
public class MovimentoCompraResource {

    private final MovimentoCompraService service;
    private final MovimentoCompraRequestConverter converter;

    @PostMapping
    public ResponseEntity<MovimentoCompra> create(@RequestBody @Valid MovimentoCompraRequest request) {
        final MovimentoCompra movimento = service.salvar(converter.encode(request));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(movimento.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
