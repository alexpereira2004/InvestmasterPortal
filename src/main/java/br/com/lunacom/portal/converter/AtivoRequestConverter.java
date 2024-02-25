package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.request.AtivoRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AtivoRequestConverter implements Converter <AtivoRequest, Ativo> {

    @Override
    public Ativo encode(AtivoRequest input) {
        return null;
    }

    @Override
    public AtivoRequest decode(Ativo input) {
        return null;
    }
}
