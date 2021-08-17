package br.com.lunacom.portal.domain.request;

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
public class DividendoRequest extends GenericRequest {
    private String id;
    private String dataRecebimento;
    private String dataRecebimentoInicio;
    private String dataRecebimentoFinal;
    private String tipo;
    private String quantidade;
    private String dividendo;
    private String valorTotal;
    private String ativoCodigo;
}







