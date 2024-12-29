package br.com.lunacom.portal.domain.response;

import br.com.lunacom.portal.domain.Contato;
import br.com.lunacom.portal.domain.Endereco;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PessoaResponse extends GenericResponse {
    private Integer id;
    private String nome;
    private LocalDate nascimento;
    private String nacionalidade;
    private String documento;
    private String genero;
    private String statusCodigo;
    private String statusDescricao;
    private List<Endereco> enderecos;
    private List<Contato> contatos;
}
