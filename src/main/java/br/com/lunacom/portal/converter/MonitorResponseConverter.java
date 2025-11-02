package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.entity.monitor.Monitor;
import br.com.lunacom.portal.domain.response.MonitorRegraResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MonitorResponseConverter
        implements Converter<MonitorRegraResponse, Monitor> {

    @Override
    public Monitor encode(MonitorRegraResponse input) {
        return null;
    }

    @Override
    public MonitorRegraResponse decode(Monitor input) {
        return MonitorRegraResponse.builder()
                .id(input.getId())
                .codigoAtivo(input.getAtivo().getCodigo())
                .prioridade(input.getPrioridade())
                .status(input.getStatus())
                .build();
    }
}
