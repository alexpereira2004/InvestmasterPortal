package br.com.lunacom.portal.domain.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MonitorRegraResponse extends GenericResponse {

    private Integer id;
    private String codigoAtivo;
    private Integer prioridade;
    private String status;
    private LocalDateTime dataCriacao;
}
