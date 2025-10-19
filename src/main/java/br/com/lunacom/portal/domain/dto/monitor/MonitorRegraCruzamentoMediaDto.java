package br.com.lunacom.portal.domain.dto.monitor;

import br.com.lunacom.portal.domain.entity.monitor.MonitorRegraCruzamentoMedia;

public class MonitorRegraCruzamentoMediaDto {
    private Integer mediaCurta;
    private Integer mediaLonga;

    public MonitorRegraCruzamentoMediaDto(MonitorRegraCruzamentoMedia entidade) {
        this.mediaCurta = entidade.getMediaCurta();
        this.mediaLonga = entidade.getMediaLonga();
    }
}
