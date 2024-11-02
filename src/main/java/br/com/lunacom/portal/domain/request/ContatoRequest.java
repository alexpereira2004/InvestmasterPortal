package br.com.lunacom.portal.domain.request;

import br.com.lunacom.portal.domain.enumeration.TipoContato;
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
    private String valor;
    private String descricao;
}
