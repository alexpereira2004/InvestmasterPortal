package br.com.lunacom.portal.resource.v2;

import br.com.lunacom.portal.converter.Converter;
import br.com.lunacom.portal.domain.entity.monitor.MonitorRegra;
import br.com.lunacom.portal.domain.request.MonitorRegraRequest;
import br.com.lunacom.portal.domain.response.MonitorRegraResponse;
import br.com.lunacom.portal.repository.GenericRepository;
import br.com.lunacom.portal.resource.v2.specification.GenericSpecification;
import br.com.lunacom.portal.resource.v2.specification.MonitorRegraSpecification;
import br.com.lunacom.portal.service.monitor.MonitorRegraService;
import br.com.lunacom.portal.util.DataUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(value="v2/monitor/regras")
public class MonitorRegraResource extends GenericController<
        MonitorRegra,
        @Valid MonitorRegraRequest,
        MonitorRegraResponse,
        MonitorRegraSpecification> {

    private MonitorRegraService service;

    public MonitorRegraResource(
            GenericRepository<MonitorRegra> repository,
            DataUtil dataUtil,
            Converter<MonitorRegraRequest, MonitorRegra> requestConverter,
            Converter<MonitorRegraResponse, MonitorRegra> responseConverter,
            @Qualifier("monitorRegraSpecification")GenericSpecification specification,
            MonitorRegraService service) {
        super(repository, dataUtil, requestConverter, responseConverter, specification);
        this.service = service;
    }
}
