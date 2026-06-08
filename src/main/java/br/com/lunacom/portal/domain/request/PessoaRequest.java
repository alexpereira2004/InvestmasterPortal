package br.com.lunacom.portal.domain.request;

import br.com.lunacom.comum.domain.enumeration.Genero;
import br.com.lunacom.comum.domain.enumeration.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

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
