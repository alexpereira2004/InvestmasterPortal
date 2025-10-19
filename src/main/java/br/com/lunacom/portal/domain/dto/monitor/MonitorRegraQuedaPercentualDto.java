package br.com.lunacom.portal.domain.dto.monitor;


import br.com.lunacom.portal.domain.entity.monitor.MonitorRegraQuedaPercentual;

public class MonitorRegraQuedaPercentualDto {
    private Double percentualQueda;
    private Integer periodoMinutos;

    public MonitorRegraQuedaPercentualDto(MonitorRegraQuedaPercentual entidade) {
        this.percentualQueda = entidade.getPercentualQueda();
        this.periodoMinutos = entidade.getPeriodoMinutos();
    }
}