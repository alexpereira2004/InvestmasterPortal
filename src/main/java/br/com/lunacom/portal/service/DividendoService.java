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

    public void salvarHtml(String request) {
        List<Dividendo> dividendoList = new ArrayList<>();
        String regex = "<div class=\\\"cont-date settlement\\\">(.*)<\\/div>\\r\\n\\s*<div class=\\\"cont-description\\\">(JUROS S\\/CAPITAL|DIVIDENDOS|RENDIMENTO|\\* PROV \\* RENDIMENTO) (\\d*)(?:\\s*|\\s*\\w*\\s)(\\w*)\\s*<\\/div>\\r\\n\\s*<div class=\\\"cont-value\\\">\\r\\n\\s*<span>(.*)<\\/span>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request);
        while (matcher.find()) {
            final Integer quantidade = Integer.valueOf(matcher.group(3));
            final Double valorTotal = StringParser.toDouble(matcher.group(5));
            final Dividendo dividendo = Dividendo.builder()
                    .dividendo(valorTotal / quantidade)
                    .dataRecebimento(dataUtil.dataBrParaLocalDate(matcher.group(1)))
                    .tipo(matcher.group(2))
                    .quantidade(quantidade)
                    .ativo(getAtivo(matcher.group(4)))
                    .valorTotal(valorTotal)
                    .build();
            dividendoList.add(dividendo);
        }
        removerDividendosExistentes(dividendoList);

        repository.saveAll(dividendoList);
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
