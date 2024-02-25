package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.response.AtivoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AtivoResponseConverter implements Converter<AtivoResponse, Ativo> {

    @Override
    public Ativo encode(AtivoResponse input) {
        return null;
    }

    @Override
    public AtivoResponse decode(Ativo input) {
        return null;
    }
}
