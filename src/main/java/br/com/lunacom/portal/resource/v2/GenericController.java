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

public class GenericController<
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

//    @GetMapping("")
//    public ResponseEntity<Page<T>> getPage(Pageable pageable){
//        return ResponseEntity.ok(service.getPage(pageable));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<T> getOne(@PathVariable Integer id){
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("")
    public ResponseEntity<T> update(@RequestBody T updated){
        return ResponseEntity.ok(service.update(updated));
    }

    @PostMapping("")
    public ResponseEntity<T> create(@RequestBody R request){
        final T created = service.save(requestConverter.encode(request));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).build();
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
