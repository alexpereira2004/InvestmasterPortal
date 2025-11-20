package br.com.lunacom.portal.converter.googlesheets;

import br.com.lunacom.comum.domain.dto.googlesheets.RendaFixaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static br.com.lunacom.portal.util.StringParser.toDouble;

@Slf4j
@Component
@RequiredArgsConstructor
public class RendaFixaRowConverter implements GoogleSheetsRowConverter<RendaFixaDto> {

    public static final String WARNING = "Leitura de Linha ignorada na integração da Renda Fixa: {}";

    @Override
    public RendaFixaDto convert(List<Object> row) {
        final List<Integer> list = Arrays.asList(1, 2, 3, 4);
        if (linhaVazia(row) || primeiraLinhaHeader(row) || tamanhoNaoPermitido(row, list)) {
            log.warn(WARNING, row);
            return null;
        }

        return parseRow(row);
    }

    private RendaFixaDto parseRow(List<?> row) {
        if (row.size() < 6 || row.size() > 7) {
            return null;
        }


        final RendaFixaDto dto = RendaFixaDto.builder()
                .data(row.get(0).toString())
                .instituicao(row.get(1).toString())
                .renda(toDouble(row.get(2).toString()))
                .investido(toDouble(row.get(3).toString()))
                .rentabilidade(toDouble(row.get(4).toString()))
                .comparaticoComCdi(toDouble(row.get(5).toString()))
                .build();

        if (row.size() == 7) {
            dto.setCdiMes(toDouble(row.get(6).toString()));
        }
        return dto;
    }

    private boolean primeiraLinhaHeader(List<Object> row) {
        return row.size() == 7 && row.get(0).toString().equals("Data");
    }
}
