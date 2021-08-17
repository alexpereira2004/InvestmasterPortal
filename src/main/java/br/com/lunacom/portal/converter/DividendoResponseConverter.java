package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.Dividendo;
import br.com.lunacom.portal.domain.response.DividendoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@AllArgsConstructor
@Component
public class DividendoResponseConverter implements Converter<DividendoResponse, Dividendo> {

    @Override
    public Dividendo encode(DividendoResponse input) {
        return null;
    }

    @Override
    public DividendoResponse decode(Dividendo input) {
        final String ativoNome = Objects.isNull(input.getAtivo()) ? "" : input.getAtivo().getNome();
        final String ativoCodigo = Objects.isNull(input.getAtivo()) ? "" : input.getAtivo().getCodigo();
        return DividendoResponse
                .builder()
                .id(input.getId())
                .dataRecebimento(input.getDataRecebimento())
                .tipo(input.getTipo())
                .quantidade(input.getQuantidade())
                .dividendo(input.getDividendo())
                .valorTotal(input.getValorTotal())
                .ativoNome(ativoNome)
                .ativoCodigo(ativoCodigo)
                .dataCriacao(input.getDataCriacao())
                .dataAtualizacao(input.getDataAtualizacao())
                .build();
    }
}
