package br.com.lunacom.portal.resource.v2;

import br.com.lunacom.portal.converter.Converter;
import br.com.lunacom.portal.domain.GenericEntity;
import br.com.lunacom.portal.domain.request.GenericRequest;
import br.com.lunacom.portal.domain.response.GenericResponse;
import br.com.lunacom.portal.repository.GenericRepository;
import br.com.lunacom.portal.resource.v2.specification.GenericSpecification;
import br.com.lunacom.portal.service.GenericService;
import br.com.lunacom.portal.util.DataUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

public abstract class GenericController<
        T extends GenericEntity<T>,
        R extends GenericRequest,
        P extends GenericResponse,
        S extends GenericSpecification> {

    private final GenericService<T, R, S> service;
    private final DataUtil dataUtil;
    private final Converter<R, T> requestConverter;
    private final Converter<P, T> responseConverter;
    private final GenericSpecification specification;

    public GenericController(
            GenericRepository<T> repository,
            DataUtil dataUtil,
            Converter<R, T> requestConverter,
            Converter<P, T> responseConverter,
            GenericSpecification specification) {
        this.requestConverter = requestConverter;
        this.responseConverter = responseConverter;
        this.service = new GenericService<T, R, S>(repository, dataUtil, specification) {};
        this.dataUtil = dataUtil;
        this.specification = specification;
    }

    @GetMapping("/{id}")
    public ResponseEntity<P> getOne(@PathVariable Integer id){
        final P response = responseConverter.decode(service.get(id));
        return ResponseEntity.ok(response);
    }

    @PutMapping("")
    public ResponseEntity<T> update(@RequestBody T updated){
        return ResponseEntity.ok(service.update(updated));
    }

    @PostMapping("")
    public ResponseEntity<T> createOne(@RequestBody R request){
        final T created = service.save(requestConverter.encode(request));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/lote")
    public ResponseEntity<String> createMany(@RequestBody List<R> request){
        service.saveAll(requestConverter.encode(request));
        return ResponseEntity.ok().build();
    }

    @GetMapping(value="/listagem-paginado")
    public ResponseEntity<Page<P>> pesquisar(R request, Pageable pageable) {
        final Page<T> page = service.pesquisarComPaginacao(request, pageable);
        final Page<P> response = page.map(e -> responseConverter.decode(e));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{stringIdList}")
    public ResponseEntity<String> delete(@PathVariable("stringIdList") List<Integer> input){
        service.deleteAll(input);
        return ResponseEntity.noContent().build();
    }
}
