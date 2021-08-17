package br.com.lunacom.portal.resource.v2;

import br.com.lunacom.portal.converter.Converter;
import br.com.lunacom.portal.domain.Dividendo;
import br.com.lunacom.portal.domain.request.DividendoRequest;
import br.com.lunacom.portal.domain.response.DividendoResponse;
import br.com.lunacom.portal.repository.GenericRepository;
import br.com.lunacom.portal.resource.v2.specification.DividendoSpecification;
import br.com.lunacom.portal.resource.v2.specification.GenericSpecification;
import br.com.lunacom.portal.util.DataUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v2/dividendo")
@Validated
public class DividendoResource extends
        GenericController<Dividendo,
                          @Valid DividendoRequest,
                          DividendoResponse,
                          DividendoSpecification> {
    public DividendoResource(
            GenericRepository<Dividendo> repository,
            DataUtil dataUtil,
            Converter<DividendoRequest, Dividendo> requestConverter,
            Converter<DividendoResponse, Dividendo> responseConverter,
            @Qualifier("dividendoSpecification") GenericSpecification specification) {
        super(repository, dataUtil, requestConverter, responseConverter, specification);
    }
}
