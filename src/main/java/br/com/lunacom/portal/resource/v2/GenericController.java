package br.com.lunacom.portal.resource.v2;

import br.com.lunacom.portal.converter.Converter;
import br.com.lunacom.portal.domain.GenericEntity;
import br.com.lunacom.portal.repository.GenericRepository;
import br.com.lunacom.portal.service.GenericService;
import br.com.lunacom.portal.util.DataUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class GenericController<T extends GenericEntity<T>,
//        R extends Converter<RequestDto,GenericEntity<T>>
        R > {

    private final GenericService<T> service;
    private final DataUtil dataUtil;
    private Converter<R, T> requestConverter;

    public GenericController(GenericRepository<T> repository, DataUtil dataUtil,
                             Converter<R, T> requestConverter) {
        this.requestConverter = requestConverter;
        this.service = new GenericService<T>(repository, dataUtil) {};
        this.dataUtil = dataUtil;

    }

    @GetMapping("")
    public ResponseEntity<Page<T>> getPage(Pageable pageable){
        return ResponseEntity.ok(service.getPage(pageable));
    }

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
        final T created = service.create(requestConverter.encode(request));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.ok("Ok");
    }
}
