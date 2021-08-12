package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.MovimentoCompra;
import br.com.lunacom.portal.domain.response.MovimentoCompraResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Component
public class MovimentoCompraResponseConverter implements Converter<MovimentoCompraResponse, MovimentoCompra>{
    @Override
    public MovimentoCompra encode(MovimentoCompraResponse input) {
        return null;
    }

    @Override
    public MovimentoCompraResponse decode(MovimentoCompra input) {
        final String ativoNome = Objects.isNull(input.getAtivo()) ? "" : input.getAtivo().getNome();
        final String ativoCodigo = Objects.isNull(input.getAtivo()) ? "" : input.getAtivo().getCodigo();
        return MovimentoCompraResponse
                .builder()
                .id(input.getId())
                .dataAquisicao(input.getDataAquisicao())
                .precoPago(input.getPrecoPago())
                .quantidade(input.getQuantidade())
                .totalInvestido(input.getTotalInvestido())
                .indicacao(input.getIndicacao())
                .estrategia(input.getEstrategia())
                .ativoNome(ativoNome)
                .ativoCodigo(ativoCodigo)
                .build();
    }
}
