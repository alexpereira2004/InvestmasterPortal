package br.com.lunacom.portal.resource.v2;

import br.com.lunacom.portal.converter.Converter;
import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.request.AtivoRequest;
import br.com.lunacom.portal.domain.response.AtivoResponse;
import br.com.lunacom.portal.repository.GenericRepository;
import br.com.lunacom.portal.resource.v2.specification.AtivoSpecification;
import br.com.lunacom.portal.resource.v2.specification.GenericSpecification;
import br.com.lunacom.portal.service.AtivoService;
import br.com.lunacom.portal.util.DataUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/v2/ativo")
public class AtivoResource extends GenericController
        <Ativo, @Valid AtivoRequest, AtivoResponse, AtivoSpecification> {
    AtivoService service;
    final Converter<AtivoResponse, Ativo> responseConverter;

    public AtivoResource(
            GenericRepository<Ativo> repository,
            DataUtil dataUtil,
            Converter<AtivoRequest, Ativo> requestConverter,
            Converter<AtivoResponse, Ativo> responseConverter,
            @Qualifier("ativoSpecification") GenericSpecification specification,
            AtivoService service) {
        super(repository, dataUtil, requestConverter, responseConverter, specification);
        this.service = service;
        this.responseConverter = responseConverter;
    }

    @GetMapping(value = "/com-dividendos")
    public ResponseEntity<List<AtivoResponse>> getAtivosComDividendos() {
        final List<AtivoResponse> response = responseConverter.decode(service.pesquisarAtivosComDividendos());
        return ResponseEntity.ok(response);
    }

}
