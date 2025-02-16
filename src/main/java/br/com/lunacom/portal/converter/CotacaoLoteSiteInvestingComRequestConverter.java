package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.Cotacao;
import br.com.lunacom.portal.domain.request.CotacaoLoteSiteInvestingComRequest;
import br.com.lunacom.portal.util.StringParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static br.com.lunacom.portal.util.StringParser.toDouble;

@AllArgsConstructor
@Component
public class CotacaoLoteSiteInvestingComRequestConverter implements Converter <
        CotacaoLoteSiteInvestingComRequest,
        Cotacao> {

    @Override
    public Cotacao encode(CotacaoLoteSiteInvestingComRequest input) {
        final Cotacao cotacao = new Cotacao();
        cotacao.setImportacao(LocalDateTime.now());
        cotacao.setPreco(toDouble(input.getUltima()));
        cotacao.setReferencia(StringParser.toLocalDate(input.getDataReferencia()));
        cotacao.setAtivo(input.getAtivo());
        cotacao.setVariacao(toDouble(input.getUltima()) - toDouble(input.getAbertura()));
        cotacao.setAbertura(toDouble(input.getAbertura()));
        cotacao.setMaxima(toDouble(input.getMaxima()));
        cotacao.setMinima(toDouble(input.getMinima()));
        cotacao.setOrigem("LoteSiteInvestingCom");
        return cotacao;
    }

    @Override
    public CotacaoLoteSiteInvestingComRequest decode(Cotacao input) {
        return null;
    }
}
