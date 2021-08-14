package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.MovimentoVenda;
import br.com.lunacom.portal.domain.response.MovimentoVendaResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@AllArgsConstructor
@Component
public class MovimentoVendaResponseConverter implements Converter<MovimentoVendaResponse, MovimentoVenda> {

    @Override
    public MovimentoVenda encode(MovimentoVendaResponse input) {
        return null;
    }

    @Override
    public MovimentoVendaResponse decode(MovimentoVenda input) {
        final String ativoNome = Objects.isNull(input.getAtivo()) ? "" : input.getAtivo().getNome();
        final String ativoCodigo = Objects.isNull(input.getAtivo()) ? "" : input.getAtivo().getCodigo();
        return MovimentoVendaResponse.builder()
                .id(input.getId())
                .dataAquisicao(input.getDataAquisicao())
                .precoPago(input.getPrecoPago())
                .quantidade(input.getQuantidade())
                .totalInvestido(input.getTotalInvestido())
                .dataVenda(input.getDataVenda())
                .precoVenda(input.getPrecoVenda())
                .totalFinal(input.getTotalFinal())
                .rendimento(input.getRendimento())
                .ativoNome(ativoNome)
                .ativoCodigo(ativoCodigo)
                .build();
    }
}
