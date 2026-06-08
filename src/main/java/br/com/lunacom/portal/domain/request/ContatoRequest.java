package br.com.lunacom.portal.domain.request;

import br.com.lunacom.comum.domain.enumeration.TipoContato;
import jakarta.validation.constraints.Size;
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
public class ContatoRequest extends GenericRequest {
    private Integer id;
    private TipoContato tipoContato;
    @Size(max = 50)
    private String valor;
    @Size(max = 100)
    private String descricao;
}
