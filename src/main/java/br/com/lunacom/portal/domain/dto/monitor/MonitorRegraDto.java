package br.com.lunacom.portal.domain.dto.monitor;

import br.com.lunacom.portal.domain.entity.monitor.MonitorRegra;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonitorRegraDto {

    private Integer id;
    private Integer ativoId;
    private Integer prioridade;
    private String status;
    private LocalDateTime dataCriacao;

    private List<MonitorRegraQuedaPercentualDto> quedas = new ArrayList<>();
    private List<MonitorRegraVolumeDto> volumes = new ArrayList<>();
    private List<MonitorRegraCruzamentoMediaDto> cruzamentos = new ArrayList<>();

    public MonitorRegraDto(MonitorRegra entidade) {
        this.id = entidade.getId();
        this.ativoId = entidade.getAtivoId();
        this.prioridade = entidade.getPrioridade();
        this.status = entidade.getStatus();
        this.dataCriacao = entidade.getDataCriacao();

        if (entidade.getRegrasQuedaPercentual() != null) {
            this.quedas = entidade.getRegrasQuedaPercentual()
                    .stream().map(MonitorRegraQuedaPercentualDto::new)
                    .collect(Collectors.toList());
        }

        if (entidade.getRegrasVolume() != null) {
            this.volumes = entidade.getRegrasVolume()
                    .stream().map(MonitorRegraVolumeDto::new)
                    .collect(Collectors.toList());
        }

        if (entidade.getRegrasCruzamentoMedia() != null) {
            this.cruzamentos = entidade.getRegrasCruzamentoMedia()
                    .stream().map(MonitorRegraCruzamentoMediaDto::new)
                    .collect(Collectors.toList());
        }
    }

}
