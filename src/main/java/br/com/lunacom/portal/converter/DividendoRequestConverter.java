package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.Dividendo;
import br.com.lunacom.portal.domain.request.DividendoRequest;
import br.com.lunacom.portal.service.AtivoService;
import br.com.lunacom.portal.util.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static br.com.lunacom.portal.util.StringParser.toDouble;
import static br.com.lunacom.portal.util.StringParser.toInteger;

@AllArgsConstructor
@Component
public class DividendoRequestConverter extends GenericConverter<DividendoRequest, Dividendo> {

    DataUtil dataUtil;
    AtivoService ativoService;

    @Override
    public Dividendo encode(DividendoRequest input) {
        final Optional<Ativo> ativo = ativoService.pesquisarPorCodigo(input.getAtivoCodigo());
        final Double dividendo = toDouble(input.getDividendo());
        final Integer quantidade = toInteger(input.getQuantidade());
        final double valorTotal = dividendo * quantidade;

        Dividendo obj = new Dividendo();
        obj.setId(toInteger(input.getId()));
        obj.setDataRecebimento(dataUtil.dataBrParaLocalDate(input.getDataRecebimento()));
        obj.setTipo(input.getTipo());
        obj.setQuantidade(quantidade);
        obj.setDividendo(dividendo);
        obj.setValorTotal(valorTotal);
        obj.setAtivo(ativo.get());

        return obj;
    }

    @Override
    public DividendoRequest decode(Dividendo input) {
        return null;
    }
}
