package br.com.lunacom.portal.domain.request;

import br.com.lunacom.portal.domain.enumeration.Genero;
import br.com.lunacom.portal.domain.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Component
public class PessoaRequest extends GenericRequest {
    private Integer id;
    @NotBlank
    @Size(max = 255)
    private String nome;
    private LocalDate nascimento;
    @Size(max = 40)
    private String nacionalidade;
    @Size(max = 40)
    private String documento;
    private Genero genero;
    private Status status;
    private List<EnderecoRequest> enderecos;
    private List<ContatoRequest> contatos;
}
