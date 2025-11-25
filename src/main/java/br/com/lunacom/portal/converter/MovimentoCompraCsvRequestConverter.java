package br.com.lunacom.portal.converter;

import br.com.lunacom.comum.domain.Ativo;
import br.com.lunacom.comum.domain.MovimentoCompra;
import br.com.lunacom.portal.domain.request.MovimentoCompraCsvRequest;
import br.com.lunacom.portal.service.AtivoService;
import br.com.lunacom.portal.util.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static br.com.lunacom.portal.util.StringParser.toInteger;

@AllArgsConstructor
@Component
public class MovimentoCompraCsvRequestConverter implements Converter <MovimentoCompraCsvRequest, MovimentoCompra>{
    DataUtil dataUtil;
    AtivoService ativoService;

    @Override
    public MovimentoCompra encode(MovimentoCompraCsvRequest input) {
        final Ativo ativo = ativoService.pesquisarPorCodigo(input.getAtivoCodigo())
                .orElse(null);
        final BigDecimal precoPago = new BigDecimal(input.getPrecoPago());
        final Integer quantidade = toInteger(input.getQuantidade());
        final BigDecimal totalInvestido = precoPago.multiply(BigDecimal.valueOf(quantidade));

        return MovimentoCompra.builder()
                .dataAquisicao(dataUtil.dataBrParaLocalDate(input.getDataAquisicao()))
                .precoPago(precoPago)
                .quantidade(quantidade)
                .totalInvestido(totalInvestido)
                .indicacao(input.getIndicacao())
                .estrategia(input.getEstrategia())
                .ativo(ativo)
                .build();
    }

    @Override
    public MovimentoCompraCsvRequest decode(MovimentoCompra input) {
        return null;
    }
}
