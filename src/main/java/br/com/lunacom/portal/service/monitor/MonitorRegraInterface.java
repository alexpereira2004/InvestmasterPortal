package br.com.lunacom.portal.service.monitor;

import br.com.lunacom.portal.domain.entity.monitor.MonitorRegra;

import java.util.List;

public interface MonitorRegraInterface {
    public String getTipo();
    List<?> buscarPorRegra(MonitorRegra regra);
}
