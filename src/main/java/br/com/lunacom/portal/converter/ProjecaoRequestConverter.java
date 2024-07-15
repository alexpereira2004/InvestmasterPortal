package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.Projecao;
import br.com.lunacom.portal.domain.request.ProjecaoRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProjecaoRequestConverter
        extends GenericConverter<ProjecaoRequest, Projecao> {
    @Override
    public Projecao encode(ProjecaoRequest input) {
        return Projecao.builder()
                .id(input.getId())
                .ano(input.getAno())
                .tipo(input.getTipo())
                .valor(input.getValor())
                .valorAlcancado(input.getValorAlcancado())
                .efetivado(input.getEfetivado())
                .observacao(input.getObservacao())
                .build();
    }

    @Override
    public ProjecaoRequest decode(Projecao input) {
        return null;
    }
}
