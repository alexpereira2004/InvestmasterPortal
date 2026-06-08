package br.com.lunacom.portal.domain.request;

import br.com.lunacom.comum.domain.enumeration.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Component
public class MonitorRegraRequest extends GenericRequest {
    private Integer id;
    private Status status = Status.ATIVO;
    @NotBlank(message = "Informe Código do Ativo (ativoVenda)")
    private String ativoCodigo;
    private Integer prioridade;
}
