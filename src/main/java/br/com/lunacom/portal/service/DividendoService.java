package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.Dividendo;
import br.com.lunacom.portal.repository.DividendoRepository;
import br.com.lunacom.portal.util.DataUtil;
import br.com.lunacom.portal.util.StringParser;
import com.google.common.collect.Iterables;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class DividendoService {
    private final DataUtil dataUtil;
    private final DividendoRepository repository;
    private final AtivoService ativoService;
    private Set<Ativo> ativoSet = new HashSet<>();
    public static final String REGEX = "<div class=\"table-content__item pointer\" role=\"button\" tabindex=\"0\">.+<soma-caption class=\"date soma-caption hydrated\">(.*)<\\/soma-caption>(?:.*\\s\\n){4}.*<soma-caption class=\"value soma-caption hydrated\">R\\$&nbsp;([\\.|\\d{1,3}]+,\\d{2}).*(CRÉDITO FRAÇÕES|JUROS S\\/CAPITAL|DIVIDENDOS|RENDIMENTO|\\* PROV \\* RENDIMENTO) (\\d*)(?:\\s*PAPEL\\s|\\s*|)(\\w*)";

    public void salvarHtml(String request) {
        List<Dividendo> dividendoList = new ArrayList<>();
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(request);
        while (matcher.find()) {
            final Integer quantidade = matcher.group(4).isEmpty() ? 1 : Integer.valueOf(matcher.group(4));
            final Double valorTotal = StringParser.toDouble(matcher.group(2));
            final Double dividendoCalculado = calcularDividendo(valorTotal, quantidade);
            final Dividendo dividendo = Dividendo.builder()
                    .dividendo(dividendoCalculado)
                    .dataRecebimento(dataUtil.dataBrParaLocalDate(matcher.group(1)))
                    .tipo(matcher.group(3))
                    .quantidade(quantidade)
                    .ativo(getAtivo(matcher.group(5)))
                    .valorTotal(valorTotal)
                    .build();
            dividendoList.add(dividendo);
        }
        removerDividendosExistentes(dividendoList);

        repository.saveAll(dividendoList);
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

}
