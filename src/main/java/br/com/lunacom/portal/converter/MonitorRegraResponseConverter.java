package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.entity.monitor.MonitorRegra;
import br.com.lunacom.portal.domain.response.MonitorRegraResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MonitorRegraResponseConverter
        implements Converter<MonitorRegraResponse, MonitorRegra> {

    @Override
    public MonitorRegra encode(MonitorRegraResponse input) {
        return null;
    }

    @Override
    public MonitorRegraResponse decode(MonitorRegra input) {
        return MonitorRegraResponse.builder()
                .id(input.getId())
                .codigoAtivo(input.getAtivo().getCodigo())
                .prioridade(input.getPrioridade())
                .status(input.getStatus())
                .build();
    }
}
