package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.MovimentoVenda;
import br.com.lunacom.portal.domain.request.MovimentoVendaRequest;
import br.com.lunacom.portal.service.AtivoService;
import br.com.lunacom.portal.util.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static br.com.lunacom.portal.util.StringParser.toDouble;
import static br.com.lunacom.portal.util.StringParser.toInteger;

@AllArgsConstructor
@Component
public class MovimentoVendaRequestConverter implements Converter<MovimentoVendaRequest, MovimentoVenda> {

    DataUtil dataUtil;
    AtivoService ativoService;

    @Override
    public MovimentoVenda encode(MovimentoVendaRequest input) {
        final Optional<Ativo> ativo = ativoService.pesquisarPorCodigo(input.getAtivoCodigo());
        final Double precoPago = toDouble(input.getPrecoPago());
        final Integer quantidade = toInteger(input.getQuantidade());
        final double totalInvestido = precoPago * quantidade;
        final Double precoVenda = toDouble(input.getPrecoVenda());
        final double totalFinal = precoVenda * quantidade;
        final double rendimento = ((precoVenda - precoPago) / precoPago) * 100;

        return MovimentoVenda.builder()
                .id(toInteger(input.getId()))
                .dataAquisicao(dataUtil.dataBrParaLocalDate(input.getDataAquisicao()))
                .precoPago(precoPago)
                .quantidade(quantidade)
                .totalInvestido(totalInvestido)
                .dataVenda(dataUtil.dataBrParaLocalDate(input.getDataVenda()))
                .precoVenda(precoVenda)
                .totalFinal(totalFinal)
                .rendimento(rendimento)
                .ativo(ativo.get())
                .build();
    }

    @Override
    public MovimentoVendaRequest decode(MovimentoVenda input) {
        return null;
    }
}
