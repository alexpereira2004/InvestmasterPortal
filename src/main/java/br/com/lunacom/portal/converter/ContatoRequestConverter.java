package br.com.lunacom.portal.converter;

import br.com.lunacom.comum.domain.Contato;
import br.com.lunacom.portal.domain.request.ContatoRequest;
import org.springframework.stereotype.Component;

@Component
public class ContatoRequestConverter
        extends GenericConverter<ContatoRequest, Contato> {

    @Override
    public Contato encode(ContatoRequest input) {
        return Contato.builder()
//                .id(input.getId())
                .tipoContato(input.getTipoContato())
                .valor(input.getValor())
                .descricao(input.getDescricao())
                .build();
    }

    @Override
    public ContatoRequest decode(Contato input) {
        return null;
    }
}
