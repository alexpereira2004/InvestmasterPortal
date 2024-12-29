package br.com.lunacom.portal.resource.v2;

import br.com.lunacom.portal.converter.Converter;
import br.com.lunacom.portal.domain.Pessoa;
import br.com.lunacom.portal.domain.request.PessoaRequest;
import br.com.lunacom.portal.domain.response.PessoaResponse;
import br.com.lunacom.portal.repository.GenericRepository;
import br.com.lunacom.portal.resource.v2.specification.GenericSpecification;
import br.com.lunacom.portal.resource.v2.specification.ProjecaoSpecification;
import br.com.lunacom.portal.service.PessoaService;
import br.com.lunacom.portal.util.DataUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/v2/pessoa")
public class PessoaResource extends GenericController
        <Pessoa, @Valid PessoaRequest, PessoaResponse, ProjecaoSpecification> {
    PessoaService service;

    public PessoaResource(
            GenericRepository<Pessoa> repository,
            DataUtil dataUtil,
            Converter<PessoaRequest, Pessoa> requestConverter,
            Converter<PessoaResponse, Pessoa> responseConverter,
            @Qualifier("projecaoSpecification")GenericSpecification specification,
            PessoaService service) {
        super(repository, dataUtil, requestConverter, responseConverter, specification);
        this.service = service;
    }
}
