package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.MovimentoCompra;
import br.com.lunacom.portal.domain.dto.MovimentoCompraCsvRequest;
import br.com.lunacom.portal.service.AtivoService;
import br.com.lunacom.portal.util.DataUtil;
import com.google.common.primitives.Doubles;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
        final Double precoPago = Doubles.tryParse(input.getPrecoPago());
        final Integer quantidade = toInteger(input.getQuantidade());
        final double totalInvestido = precoPago * quantidade;
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
