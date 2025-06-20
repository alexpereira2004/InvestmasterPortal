package br.com.lunacom.portal.converter.googlesheets;

import br.com.lunacom.portal.domain.AporteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static br.com.lunacom.portal.domain.enumeration.FormatosData.DD_MM_YYYY_BARRA;
import static br.com.lunacom.portal.util.StringParser.toDouble;
import static br.com.lunacom.portal.util.StringParser.toLocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AporteRowConverter implements GoogleSheetsRowConverter<AporteDto> {

    public static final String WARNING = "Leitura de Linha ignorada na integração de Aportes: {}";

    @Override
    public AporteDto convert(List<Object> row) {
        if (linhaVazia(row) || primeiraLinhaHeader(row)) {
            log.warn(WARNING, row);
            return null;
        }

        final AporteDto dto = AporteDto.builder()
                .dataAporte(toLocalDate(row.get(0).toString(), DD_MM_YYYY_BARRA))
                .valor(BigDecimal.valueOf(toDouble(row.get(1).toString())))
                .origem(row.get(2).toString())
                .destino(row.get(3).toString())
                .build();
        return dto;
    }

    private boolean primeiraLinhaHeader(List<Object> row) {
        return row.size() == 4 && row.get(0).toString().equals("Data");
    }
}
