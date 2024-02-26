package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.request.AtivoRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AtivoRequestConverter extends GenericConverter <AtivoRequest, Ativo> {

    @Override
    public Ativo encode(AtivoRequest input) {
        return Ativo.builder()
                .id(input.getId())
                .nome(input.getNome())
                .nomeCompleto(input.getNomeCompleto())
                .codigo(input.getCodigo())
                .tipo(input.getTipo())
                .pais(input.getPais())
                .caminho(input.getCaminho())
                .seguindo(input.getSeguindo())
                .build();
    }

    @Override
    public AtivoRequest decode(Ativo input) {
        return null;
    }
}
