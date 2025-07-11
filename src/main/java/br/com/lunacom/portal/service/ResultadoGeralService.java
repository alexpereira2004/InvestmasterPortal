package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.Carteira;
import br.com.lunacom.portal.domain.CotacaoAgoraDto;
import br.com.lunacom.portal.domain.dto.AtivoDividendoDto;
import br.com.lunacom.portal.domain.dto.DividendoAnual;
import br.com.lunacom.portal.domain.enumeration.Periodicidade;
import br.com.lunacom.portal.domain.request.ExtratoDividendosRequest;
import br.com.lunacom.portal.domain.response.ExtratoDividendoResponse;
import br.com.lunacom.portal.domain.response.ResultadoGeralResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Log4j2
public class ResultadoGeralService {

    private final CarteiraService carteiraService;
    private final CotacaoService cotacaoService;
    private final DividendoService dividendoService;

    public static final String MSG_ATIVO_NAO_EXISTE = "O ativo %s não possui dados na Carteira. Usar o serviço de importação/integração com planilhas Google deve resolver.";


    public ResultadoGeralService(CarteiraService carteiraService,
                                 CotacaoService cotacaoService,
                                 @Lazy DividendoService dividendoService) {
        this.carteiraService = carteiraService;
        this.cotacaoService = cotacaoService;
        this.dividendoService = dividendoService;
    }

    public ResultadoGeralResponse pesquisarResultadoGeral(String ativo) {

        final List<Carteira> carteiraList = carteiraService.pesquisar();

        final Carteira carteira = getCarteira(ativo, carteiraList);

        final CotacaoAgoraDto cotacaoAgoraDto = getCotacaoAgora(ativo);

        final List<DividendoAnual> dividendoAnualList = getDividendoAnualList(ativo, cotacaoAgoraDto);

        BigDecimal totalDividendos = calcularTotalDividendos(dividendoAnualList);

        final BigDecimal totalAtualizadoComDividendos = carteira
                .getTotalAtualizado()
                .add(totalDividendos);

        final BigDecimal proporcaoTotalInvestido
                = calcularProporcaoDoTotalInvestido(carteiraList, carteira);

        final BigDecimal proporcaoTipoAtivoInvestido
                = calcularProporcaoPorTipoInvestido(carteiraList, carteira);

        final BigDecimal resultado = calcularResultado(carteira);

        final BigDecimal resultadoPercentual = calcularResultadoPercentual(carteira, resultado);

        final BigDecimal resultadoComDividendo = calcularResultadoComDividendos(totalDividendos, resultado);

        final BigDecimal resultadoComDividendoPercentual
                = calcularResultadoComDividendosPercentual(carteira, resultadoComDividendo);


        return ResultadoGeralResponse.builder()
                .precoMedio(carteira.getPrecoPago())
                .cotacaoAtual(cotacaoAgoraDto.getCotacaoAtual())
                .quantidadeCotas(carteira.getQuantidade())
                .investimentoTotal(carteira.getTotalInvestido())
                .investimentoTotalAtualizado(carteira.getTotalAtualizado())
                .investimentoTotalAtualizadoComDividendos(totalAtualizadoComDividendos)
                .proporcaoTotalInvestido(proporcaoTotalInvestido)
                .proporcaoTipoAtivoInvestido(proporcaoTipoAtivoInvestido)
                .resultado(resultado)
                .resultadoPercentual(resultadoPercentual)
                .resultadoComDividendo(resultadoComDividendo)
                .resultadoComDividendoPercentual(resultadoComDividendoPercentual)
                .dividendYeld(BigDecimal.ZERO)
                .dividendos(dividendoAnualList)
                .build();
    }

    private BigDecimal calcularResultadoComDividendosPercentual(Carteira carteira, BigDecimal resultadoComDividendo) {
        return resultadoComDividendo
                .divide(carteira.getTotalInvestido(), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private BigDecimal calcularResultadoComDividendos(BigDecimal totalDividendos, BigDecimal resultado) {
        return resultado.add(totalDividendos);
    }

    private BigDecimal calcularResultadoPercentual(Carteira carteira, BigDecimal resultado) {
        return resultado
                .divide(carteira.getTotalInvestido(), 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private BigDecimal calcularResultado(Carteira carteira) {
        return carteira.getTotalAtualizado().subtract(carteira.getTotalInvestido());
    }

    private BigDecimal calcularProporcaoDoTotalInvestido(List<Carteira> carteiraList, Carteira carteira) {
        final BigDecimal total = carteiraList.stream()
                .map(Carteira::getTotalInvestido)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return carteira
                .getTotalInvestido()
                .divide(total, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private BigDecimal calcularProporcaoPorTipoInvestido(List<Carteira> carteiraList, Carteira carteira) {
        final BigDecimal total = carteiraList.stream()
                .filter(c -> c.getAtivo().getTipo().equals(carteira.getAtivo().getTipo()))
                .map(Carteira::getTotalInvestido)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return carteira
                .getTotalInvestido().divide(total, 2, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private List<DividendoAnual> getDividendoAnualList(String ativo, CotacaoAgoraDto cotacaoAgoraDto) {
        final ExtratoDividendosRequest request = ExtratoDividendosRequest.builder()
                .codigos(Arrays.asList(ativo))
                .periodicidade(Periodicidade.ANUAL.getDescricao().toLowerCase(Locale.ROOT))
                .build();
        final ExtratoDividendoResponse extratoDividendoResponse = dividendoService.pesquisarExtratoDividendos(request);

        final List<DividendoAnual> dividendoAnualList = extratoDividendoResponse
                .getDividendos().stream()
                .map(r -> conversaoEspecifica(cotacaoAgoraDto, r))
                .collect(Collectors.toList());
        return dividendoAnualList;
    }

    private CotacaoAgoraDto getCotacaoAgora(String ativo) {
        return cotacaoService
                .pesquisarCotacaoAgora().stream()
                .filter(c -> c.getCodigo().equals(ativo)).findFirst()
                .orElse(new CotacaoAgoraDto());
    }


    private Carteira getCarteira(String ativo, List<Carteira> carteiraList) {
        final Optional<Carteira> optional = carteiraList.stream()
                .filter(c -> c.getAtivo().getCodigo().equals(ativo)).findFirst();

        final Carteira carteira = optional.orElseThrow(
                () -> new NoSuchElementException(format(MSG_ATIVO_NAO_EXISTE, ativo)));
        return carteira;
    }

    private BigDecimal calcularTotalDividendos(List<DividendoAnual> dividendoAnualList) {
        return dividendoAnualList.stream()
                .map(DividendoAnual::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private DividendoAnual conversaoEspecifica(CotacaoAgoraDto cotacaoAgoraDto, AtivoDividendoDto r) {
        return DividendoAnual.builder()
                .ano(r.getUltimoDividendo().getYear())
                .valor(r.getValorTotal())
                .quantidade(r.getQuantidadeMaxima())
                .dividendYeldAtualizado(calcularDyAtualizado(r, cotacaoAgoraDto.getCotacaoAtual()))
                .build();
    }

    private BigDecimal calcularDyAtualizado(AtivoDividendoDto r, BigDecimal cotacaoAtual) {
        final BigDecimal totalInvestido = cotacaoAtual.multiply(BigDecimal.valueOf(r.getQuantidadeMaxima()));
        BigDecimal resultado = r.getValorTotal()
                .divide(totalInvestido, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100l));
        return resultado;
    }
}
