package br.com.lunacom.portal.converter;

import br.com.lunacom.comum.domain.Pessoa;
import br.com.lunacom.comum.domain.enumeration.Status;
import br.com.lunacom.portal.domain.response.PessoaResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PessoaResponseConverter implements Converter<PessoaResponse, Pessoa> {
    @Override
    public Pessoa encode(PessoaResponse input) {
        return null;
    }

    @Override
    public PessoaResponse decode(Pessoa input) {
        return PessoaResponse.builder()
                .id(input.getId())
                .nome(input.getNome())
                .nascimento(input.getNascimento())
                .nacionalidade(input.getNacionalidade())
                .documento(input.getDocumento())
                .genero(input.getGenero())
                .statusCodigo(Status.fromCodigo(input.getStatus()).getCodigo())
                .statusDescricao(Status.fromCodigo(input.getStatus()).getDescricao())
                .enderecos(input.getEnderecos())
                .contatos(input.getContatos())
                .build();
    }
}
