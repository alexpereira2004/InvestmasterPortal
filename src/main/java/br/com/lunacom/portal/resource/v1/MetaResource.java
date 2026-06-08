package br.com.lunacom.portal.resource.v1;

import br.com.lunacom.comum.domain.entity.meta.Meta;
import br.com.lunacom.portal.service.MetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/v1/meta")
public class MetaResource {

    private final MetaService service;

//    @GetMapping("")
//    public List<Meta> lerTodos() {
//
//        GoogleSheetsDataServiceInterface<?> service =  factory.getService(tipo);
//        return service.lerPlanilha(dto);
//
//    }

    @GetMapping("/{id}")
    public Optional<Meta> lerUm(@PathVariable Integer id) {
        final Optional<Meta> meta = this.service.pesquisarPorId(id);
        return meta;

    }
}
