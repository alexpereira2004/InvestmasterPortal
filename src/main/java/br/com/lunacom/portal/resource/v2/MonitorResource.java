package br.com.lunacom.portal.resource.v2;

import br.com.lunacom.comum.domain.entity.monitor.Monitor;
import br.com.lunacom.portal.converter.Converter;
import br.com.lunacom.portal.domain.request.MonitorRegraRequest;
import br.com.lunacom.portal.domain.response.MonitorRegraResponse;
import br.com.lunacom.portal.repository.GenericRepository;
import br.com.lunacom.portal.resource.v2.specification.GenericSpecification;
import br.com.lunacom.portal.resource.v2.specification.MonitorSpecification;
import br.com.lunacom.portal.service.monitor.MonitorService;
import br.com.lunacom.portal.util.DataUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(value="v2/monitor")
public class MonitorResource extends GenericController<
        Monitor,
        @Valid MonitorRegraRequest,
        MonitorRegraResponse,
        MonitorSpecification> {

    private MonitorService service;

    public MonitorResource(
            GenericRepository<Monitor> repository,
            DataUtil dataUtil,
            Converter<MonitorRegraRequest, Monitor> requestConverter,
            Converter<MonitorRegraResponse, Monitor> responseConverter,
            @Qualifier("monitorSpecification")GenericSpecification specification,
            MonitorService service) {
        super(repository, dataUtil, requestConverter, responseConverter, specification);
        this.service = service;
    }
}
