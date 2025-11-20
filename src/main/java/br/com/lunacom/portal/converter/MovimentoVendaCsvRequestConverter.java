package br.com.lunacom.portal.converter;

import br.com.lunacom.comum.domain.Ativo;
import br.com.lunacom.portal.domain.MovimentoVenda;
import br.com.lunacom.portal.domain.request.MovimentoVendaCsvRequest;
import br.com.lunacom.portal.service.AtivoService;
import br.com.lunacom.portal.util.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static br.com.lunacom.portal.util.StringParser.toDouble;
import static br.com.lunacom.portal.util.StringParser.toInteger;

@AllArgsConstructor
@Component
public class MovimentoVendaCsvRequestConverter implements Converter<MovimentoVendaCsvRequest, MovimentoVenda> {
    DataUtil dataUtil;
    AtivoService ativoService;

    @Override
    public MovimentoVenda encode(MovimentoVendaCsvRequest input) {
        final Ativo ativo = ativoService.pesquisarPorCodigo(input.getAtivoCodigo())
                .orElse(null);

        final Double precoPago = toDouble(input.getPrecoPago());
        final Integer quantidade = toInteger(input.getQuantidade());
        final double totalInvestido = precoPago * quantidade;
        final Double precoVenda = toDouble(input.getPrecoVenda());
        final double totalFinal = precoVenda * quantidade;
        final double rendimento = (precoVenda - precoVenda) / precoPago;

        return MovimentoVenda.builder()
                .dataAquisicao(dataUtil.dataBrParaLocalDate(input.getDataAquisicao()))
                .precoPago(precoPago)
                .quantidade(quantidade)
                .totalInvestido(totalInvestido)
                .dataVenda(dataUtil.dataBrParaLocalDate(input.getDataVenda()))
                .precoVenda(precoVenda)
                .totalFinal(totalFinal)
                .rendimento(rendimento)
                .ativo(ativo)
                .build();
    }

    @Override
    public MovimentoVendaCsvRequest decode(MovimentoVenda input) {
        return null;
    }
}
