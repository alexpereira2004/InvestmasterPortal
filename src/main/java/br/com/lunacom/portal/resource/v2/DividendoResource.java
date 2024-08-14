package br.com.lunacom.portal.resource.v2;

import br.com.lunacom.portal.converter.Converter;
import br.com.lunacom.portal.domain.Dividendo;
import br.com.lunacom.portal.domain.dto.MediaDividendosDto;
import br.com.lunacom.portal.domain.request.DividendoRequest;
import br.com.lunacom.portal.domain.response.DividendoResponse;
import br.com.lunacom.portal.repository.GenericRepository;
import br.com.lunacom.portal.resource.v2.specification.DividendoSpecification;
import br.com.lunacom.portal.resource.v2.specification.GenericSpecification;
import br.com.lunacom.portal.service.DividendoService;
import br.com.lunacom.portal.util.DataUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/v2/dividendo")
@Validated
public class DividendoResource extends
        GenericController<Dividendo,
                          @Valid DividendoRequest,
                          DividendoResponse,
                          DividendoSpecification> {
    DividendoService service;

    public DividendoResource(
            GenericRepository<Dividendo> repository,
            DataUtil dataUtil,
            Converter<DividendoRequest, Dividendo> requestConverter,
            Converter<DividendoResponse, Dividendo> responseConverter,
            @Qualifier("dividendoSpecification") GenericSpecification specification,
            DividendoService service) {
        super(repository, dataUtil, requestConverter, responseConverter, specification);
        this.service = service;
    }


    @PostMapping(value = "/importacao-html")
    public ResponseEntity<Void> createFromCsvText(@RequestBody @Valid @NotNull String request) {
        service.salvarHtml(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/media")
    public ResponseEntity<MediaDividendosDto> media(@RequestParam(defaultValue = "T") String tipo) {
        final MediaDividendosDto mediaDividendos = service.getMediaDividendos();
        return ResponseEntity.ok(mediaDividendos);
    }
}
