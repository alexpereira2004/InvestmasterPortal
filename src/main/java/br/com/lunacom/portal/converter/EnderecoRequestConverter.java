package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.Endereco;
import br.com.lunacom.portal.domain.enumeration.TipoEndereco;
import br.com.lunacom.portal.domain.request.EnderecoRequest;
import org.springframework.stereotype.Component;

@Component
public class EnderecoRequestConverter
        extends GenericConverter<EnderecoRequest, Endereco> {

    @Override
    public Endereco encode(EnderecoRequest input) {
        return Endereco.builder()
                .principal(input.getPrincipal())
                .rua(input.getRua())
                .numero(input.getNumero())
                .complemento(input.getComplemento())
                .bairro(input.getBairro())
                .cidade(input.getCidade())
                .estado(input.getEstado())
                .cep(input.getCep())
                .tipoEndereco(TipoEndereco.fromCodigo(input.getTipoEndereco()).getCodigo())
                .observacao(input.getObservacao())
                .build();
    }

    @Override
    public EnderecoRequest decode(Endereco input) {
        return null;
    }
}
