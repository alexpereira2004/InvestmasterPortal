package br.com.lunacom.portal.resource.v2;

import br.com.lunacom.portal.converter.Converter;
import br.com.lunacom.portal.domain.Projecao;
import br.com.lunacom.portal.domain.request.ProjecaoRequest;
import br.com.lunacom.portal.domain.response.ProjecaoResponse;
import br.com.lunacom.portal.repository.GenericRepository;
import br.com.lunacom.portal.resource.v2.specification.GenericSpecification;
import br.com.lunacom.portal.resource.v2.specification.ProjecaoSpecification;
import br.com.lunacom.portal.service.ProjecaoService;
import br.com.lunacom.portal.util.DataUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/v2/projecao")
public class ProjecaoResource extends GenericController
        <Projecao, @Valid ProjecaoRequest, ProjecaoResponse, ProjecaoSpecification> {
    ProjecaoService service;

    public ProjecaoResource(
            GenericRepository<Projecao> repository,
            DataUtil dataUtil,
            Converter<ProjecaoRequest, Projecao> requestConverter,
            Converter<ProjecaoResponse, Projecao> responseConverter,
            @Qualifier("projecaoSpecification")GenericSpecification specification,
            ProjecaoService service) {
        super(repository, dataUtil, requestConverter, responseConverter, specification);
        this.service = service;
    }
}
