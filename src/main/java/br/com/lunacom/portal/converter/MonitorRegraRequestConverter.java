package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.entity.monitor.MonitorRegra;
import br.com.lunacom.portal.domain.request.MonitorRegraRequest;
import org.springframework.stereotype.Component;

@Component
public class MonitorRegraRequestConverter extends GenericConverter<MonitorRegraRequest, MonitorRegra> {


    @Override
    public MonitorRegra encode(MonitorRegraRequest input) {
        return MonitorRegra.builder()
                .id(input.getId())
//                .ativoId(input.getAtivoId())
//                .prioridade(input.getPrioridade())
                .status(input.getStatus())
                .build();
    }

    @Override
    public MonitorRegraRequest decode(MonitorRegra input) {
        return null;
    }
}
