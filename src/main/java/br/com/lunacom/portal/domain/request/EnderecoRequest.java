package br.com.lunacom.portal.domain.request;

import br.com.lunacom.portal.domain.enumeration.Boleano;
import br.com.lunacom.portal.domain.enumeration.TipoEndereco;
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
public class EnderecoRequest extends GenericRequest {
    private Integer id;
    private Boleano principal;
    @Size(max = 100)
    private String rua;
    @Size(max = 10)
    private String numero;
    @Size(max = 50)
    private String complemento;
    @Size(max = 50)
    private String bairro;
    @Size(max = 50)
    private String cidade;
    @Size(max = 2)
    private String estado;
    @Size(max = 10)
    private String cep;
    private TipoEndereco tipoEndereco;
    @Size(max = 100)
    private String observacao;
}
