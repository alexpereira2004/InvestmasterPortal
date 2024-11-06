package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.Contato;
import br.com.lunacom.portal.domain.Endereco;
import br.com.lunacom.portal.domain.Pessoa;
import br.com.lunacom.portal.domain.request.PessoaRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class PessoaRequestConverter
        extends GenericConverter<PessoaRequest, Pessoa> {

    private EnderecoRequestConverter enderecoRequestConverter;
    private ContatoRequestConverter contatoRequestConverter;

    @Override
    public Pessoa encode(PessoaRequest input) {
        final Pessoa pessoa = Pessoa.builder()
                .nome(input.getNome())
                .nascimento(input.getNascimento())
                .nacionalidade(input.getNacionalidade())
                .documento(input.getDocumento())
                .genero(input.getGenero().getCodigo())
                .status(input.getStatus().getCodigo())
                .build();

        final List<Endereco> enderecoList = enderecoRequestConverter
                .encode(input.getEnderecos());
        enderecoList.forEach(e -> e.setPessoa(pessoa));
        pessoa.setEnderecos(enderecoList);

        final List<Contato> contatoList = contatoRequestConverter
                .encode(input.getContatos());
        contatoList.forEach( e -> e.setPessoa(pessoa));
        pessoa.setContatos(contatoList);

        return pessoa;

    }

    @Override
    public PessoaRequest decode(Pessoa input) {
        return null;
    }
}
