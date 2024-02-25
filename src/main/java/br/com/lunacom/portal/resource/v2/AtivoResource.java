package br.com.lunacom.portal.resource.v2;

import br.com.lunacom.portal.converter.Converter;
import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.request.AtivoRequest;
import br.com.lunacom.portal.domain.response.AtivoResponse;
import br.com.lunacom.portal.repository.GenericRepository;
import br.com.lunacom.portal.resource.v2.specification.AtivoSpecification;
import br.com.lunacom.portal.util.DataUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/v2/ativo")
public class AtivoResource extends GenericController
        <Ativo, @Valid AtivoRequest, AtivoResponse, AtivoSpecification> {


    public AtivoResource(
            GenericRepository<Ativo> repository,
            DataUtil dataUtil,
            Converter<@Valid AtivoRequest, Ativo> requestConverter,
            Converter<AtivoResponse, Ativo> responseConverter,
            AtivoSpecification specification) {
        super(repository, dataUtil, requestConverter, responseConverter, specification);
    }

}
