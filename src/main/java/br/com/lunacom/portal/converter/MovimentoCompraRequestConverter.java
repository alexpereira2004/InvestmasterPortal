package br.com.lunacom.portal.converter;


import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.MovimentoCompra;
import br.com.lunacom.portal.domain.dto.MovimentoCompraRequest;
import br.com.lunacom.portal.service.AtivoService;
import br.com.lunacom.portal.util.DataUtil;
import br.com.lunacom.portal.util.StringParser;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static br.com.lunacom.portal.util.StringParser.*;

@AllArgsConstructor
@Component
public class MovimentoCompraRequestConverter implements Converter<MovimentoCompraRequest, MovimentoCompra> {

    DataUtil dataUtil;
    AtivoService ativoService;

    @Override
    public MovimentoCompra encode(MovimentoCompraRequest input) {

        final Optional<Ativo> ativo = ativoService.pesquisarPorCodigo(input.getAtivoCodigo());
        final Double precoPago = Doubles.tryParse(input.getPrecoPago());
        final Integer quantidade = toInteger(input.getQuantidade());
        final double totalInvestido = precoPago * quantidade;
        return MovimentoCompra.builder()
                .id(toInteger(input.getId()))
                .dataAquisicao(dataUtil.dataBrParaLocalDate(input.getDataAquisicao()))
                .precoPago(precoPago)
                .quantidade(quantidade)
                .totalInvestido(totalInvestido)
                .indicacao(input.getIndicacao())
                .estrategia(input.getEstrategia())
                .ativo(ativo.get())
                .build();
    }

    @Override
    public MovimentoCompraRequest decode(MovimentoCompra input) {
        return null;
    }
}
