package br.com.lunacom.portal.domain.request;

import br.com.lunacom.portal.domain.enumeration.TipoContato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;

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
