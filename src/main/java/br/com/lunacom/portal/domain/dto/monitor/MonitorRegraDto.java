package br.com.lunacom.portal.domain.dto.monitor;

import br.com.lunacom.portal.domain.entity.monitor.MonitorRegra;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Map<String, List<?>> resultados = new HashMap<>();

    public MonitorRegraDto(MonitorRegra regra) {
        this.id = regra.getId();
        this.ativoId  = regra.getAtivoId();
        this.prioridade  = regra.getPrioridade();
        this.status  = regra.getStatus();
        this.dataCriacao = regra.getDataCriacao();
    }

    public <T> void adicionarResultado(String tipo, List<T> lista) {
        resultados.put(tipo, lista);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> obterResultado(String tipo) {
        return (List<T>) resultados.getOrDefault(tipo, Collections.emptyList());
    }



}
