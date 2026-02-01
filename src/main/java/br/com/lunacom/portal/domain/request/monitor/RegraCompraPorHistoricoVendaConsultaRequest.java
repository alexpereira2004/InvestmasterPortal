package br.com.lunacom.portal.domain.request.monitor;

import br.com.lunacom.comum.domain.enumeration.Status;
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
public class RegraCompraPorHistoricoVendaConsultaRequest {
    private Status status;
}
