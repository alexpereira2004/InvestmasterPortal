package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.Dividendo;
import br.com.lunacom.portal.domain.dto.AtivoDividendoDto;
import br.com.lunacom.portal.domain.dto.MediaDividendosDto;
import br.com.lunacom.portal.domain.request.ExtratoDividendosRequest;
import br.com.lunacom.portal.domain.response.DividendosImportadosResumoResponse;
import br.com.lunacom.portal.domain.response.ExtratoDividendoResponse;
import br.com.lunacom.portal.repository.DividendoRepository;
import br.com.lunacom.portal.util.DataUtil;
import br.com.lunacom.portal.util.StringParser;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Log4j2
public class DividendoService {
    private final DataUtil dataUtil;
    private final DividendoRepository repository;
    private final AtivoService ativoService;
    private Set<Ativo> ativoSet = new HashSet<>();
//    public static final String REGEX = "<div class=\"table-content__item pointer\" role=\"button\" tabindex=\"0\">.+<soma-caption class=\"date soma-caption hydrated\">(.*)<\\/soma-caption>(?:.*\\s\\n){4}.*<soma-caption class=\"value soma-caption hydrated\">R\\$&nbsp;([\\.|\\d{1,3}]+,\\d{2}).*(CRÉDITO FRAÇÕES|JUROS S\\/CAPITAL|DIVIDENDOS|RENDIMENTO|\\* PROV \\* RENDIMENTO)\\s+([\\d*,]*\\d*)(?:\\s*PAPEL\\s|\\s*|)(\\w*)";
    public static final String REGEX = "(\\d{1,2} DE .+ DE \\d{4})|(?:Entrada|ENTRADA)\\t(Juros Sobre Capital Próprio|Dividendo|Rendimento)\\t(\\w{4}\\d{1,2}).*\\s\\n.*\\s.*\\n((\\d\\.*\\d+))\\tR\\$ ([\\.|\\d{1,3}]+,\\d{2})\\tR\\$ ([\\.|\\d{1,3}]+,\\d{2})";

    public void salvarHtml(String request) {
        List<Dividendo> dividendoList = new ArrayList<>();
        LocalDate dataRecebimento = null;
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(request);

        while (matcher.find()) {
            if (Objects.nonNull(matcher.group(1))) {
                dataRecebimento = dataUtil.dataEmExtensoParaLocalDate(matcher.group(1));
            } else {
                String tipo = matcher.group(2);
                Ativo ativo = getAtivo(matcher.group(3));
                final Integer quantidade = matcher.group(4).isEmpty() ? 1 : getInteger(matcher);
                final Double dividendoCalculado = StringParser.toDouble(matcher.group(6));
                final Double valorTotal = StringParser.toDouble(matcher.group(7));

                final Dividendo dividendo = Dividendo.builder()
                        .dividendo(dividendoCalculado)
                        .dataRecebimento(dataRecebimento)
                        .tipo(tipo)
                        .quantidade(quantidade)
                        .ativo(ativo)
                        .valorTotal(valorTotal)
                        .build();
                dividendoList.add(dividendo);
            }

        }
        removerDividendosExistentes(dividendoList);

        repository.saveAll(dividendoList);
    }

    private Integer getInteger(Matcher matcher) {
        final String replace = matcher.group(4).replace(".", "");
        return Integer.valueOf(replace);
    }

    private Double calcularDividendo(Double valorTotal, Integer quantidade) {
        return valorTotal / quantidade;
    }

    private void removerDividendosExistentes(List<Dividendo> dividendoList) {
        if (dividendoList.isEmpty()) {
            return;
        }
        dividendoList.sort(Comparator.comparing(o -> o.getDataRecebimento()));
        final Dividendo first = Iterables.getFirst(dividendoList, null);
        final Dividendo last = Iterables.getLast(dividendoList);
        final List<Dividendo> byDataRecebimentoBetween = repository
                .findByDataRecebimentoBetween(
                        first.getDataRecebimento(),
                        last.getDataRecebimento());
        repository.deleteAll(byDataRecebimentoBetween);
    }

    private Ativo getAtivo(String ativoCodigo) {
        final Optional<Ativo> optional = ativoSet.stream().filter(e -> e.getCodigo().equals(ativoCodigo)).findFirst();
        return optional.orElse(pesquisarNoBanco(ativoCodigo));
    }

    private Ativo pesquisarNoBanco(String ativoCodigo) {
        return ativoService.pesquisarPorCodigo(ativoCodigo).orElse(null);
    }

    public MediaDividendosDto getMediaDividendos() {
        return MediaDividendosDto.builder()
                .total(repository.getMediaDividendosTotal())
                .acoes(repository.getMediaDividendosAcoes())
                .fundos(repository.getMediaDividendosFundos())
                .outros(repository.getMediaDividendosOutros())
                .build();
    }

    public ExtratoDividendoResponse pesquisarExtratoDividendos(ExtratoDividendosRequest request) {

        Map<String,String> periodicidades = ImmutableMap.of(
                "diario", "%d%m%Y",
                "mensal", "%m%Y",
                "anual", "%Y"
        );
        final List<AtivoDividendoDto> extrato = repository.getExtrato(
                request.getCodigos(),
                periodicidades.get(request.getPeriodicidade()),
                request.getDataInicio(),
                request.getDataFim()
        );

        final ExtratoDividendoResponse response = ExtratoDividendoResponse
                .builder().dividendos(extrato).build();

        if (!extrato.isEmpty()) {
            final List<String> labels = gerarMetaDadosDividendos(extrato, request.getPeriodicidade());
            response.setLabel(labels);
        }


        return response;
    }

    private List<String> gerarMetaDadosDividendos(List<AtivoDividendoDto> extrato, String periodicidade) {
        final LocalDate primeiroDividendo = extrato.get(0).getPrimeiroDividendo();
        final LocalDate ultimoDividendo = extrato.get(extrato.size() - 1).getPrimeiroDividendo();

        final List<String> strings = periodicidade.equals("anual")
                ? gerarListaAnos(primeiroDividendo, ultimoDividendo)
                : gerarListaMeses(primeiroDividendo, ultimoDividendo);
        return strings;

    }

    public static List<String> gerarListaMeses(LocalDate inicio, LocalDate fim) {
        List<String> meses = new ArrayList<>();
        YearMonth anoMes = YearMonth.from(inicio);
        YearMonth anoMesFim = YearMonth.from(fim);

        while (anoMes.compareTo(anoMesFim) <= 0) {
            meses.add(anoMes.toString());
            anoMes = anoMes.plusMonths(1);
        }

        return meses;
    }

    public static List<String> gerarListaAnos(LocalDate inicio, LocalDate fim) {
        List<String> anos = new ArrayList<>();
        int anoAtual = inicio.getYear();
        int anoFim = fim.getYear();

        while (anoAtual <= anoFim) {
            anos.add(String.valueOf(anoAtual));
            anoAtual++;
        }

        return anos;
    }

    public DividendosImportadosResumoResponse getInformacoesDividendosImportados() {
        final Dividendo ultimoDividendo = repository.findFirstByOrderByDataRecebimentoDesc();
        final DividendosImportadosResumoResponse response = new DividendosImportadosResumoResponse();
        response.setDataRecebimento(ultimoDividendo.getDataRecebimento());
        response.setTipo(ultimoDividendo.getTipo());
        response.setQuantidade(ultimoDividendo.getQuantidade());
        response.setDividendo(ultimoDividendo.getDividendo());
        response.setValorTotal(ultimoDividendo.getValorTotal());
        response.setAtivoNome(ultimoDividendo.getAtivo().getNome());
        response.setAtivoCodigo(ultimoDividendo.getAtivo().getCodigo());
        response.setDataCriacao(ultimoDividendo.getDataCriacao());
        response.setDataAtualizacao(ultimoDividendo.getDataAtualizacao());
        response.setTotalDividendosImportados(repository.count());
        return response;
    }

}
