package br.com.lunacom.portal.converter;

import br.com.lunacom.comum.domain.Projecao;
import br.com.lunacom.portal.domain.response.ProjecaoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProjecaoResponseConverter implements Converter<ProjecaoResponse, Projecao> {
    @Override
    public Projecao encode(ProjecaoResponse input) {
        return null;
    }

    @Override
    public ProjecaoResponse decode(Projecao input) {
        return ProjecaoResponse.builder()
                .id(input.getId())
                .ano(input.getAno())
                .mes(input.getMes())
                .tipo(input.getTipo())
                .valor(input.getValor())
                .valorAlcancado(input.getValorAlcancado())
                .efetivado(input.getEfetivado())
                .observacao(input.getObservacao())
                .build();
    }
}
