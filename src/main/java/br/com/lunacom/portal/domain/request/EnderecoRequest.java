package br.com.lunacom.portal.domain.request;

import br.com.lunacom.portal.domain.enumeration.Boleano;
import br.com.lunacom.portal.domain.enumeration.TipoEndereco;
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
public class EnderecoRequest extends GenericRequest {
    private Integer id;
    private Boleano principal;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private TipoEndereco tipoEndereco;
    private String observacao;
}
