package br.com.lunacom.portal.converter.googlesheets;

import br.com.lunacom.portal.domain.dto.googlesheets.CarteiraDto;
import br.com.lunacom.portal.util.DataUtil;
import br.com.lunacom.portal.util.StringParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ComprasRowConverter implements GoogleSheetsRowConverter<CarteiraDto> {

    public static final String MSG_MOTIVO_ERRO = "Linha ignorada por estar vazia ou incompleta na conversão de dados da Carteira: {}";
    private final DataUtil dataUtil;

    @Override
    public CarteiraDto convert(List<Object> row) {

        if (row == null || row.isEmpty() || row.size() < 1) {
            log.warn(MSG_MOTIVO_ERRO, row);
            return null;
        }

        if (row.size() == 1) {
            return CarteiraDto.builder()
                    .header(check(row, 0))
                    .build();
        }

        Set<Integer> tamanhosValidos = new HashSet<>(Arrays.asList(10, 12, 14));

        if (tamanhosValidos.contains(row.size())) {
            return CarteiraDto.builder()
                    .codigoAtivo(check(row, 0))
                    .estrategia(check(row, 1))
                    .precoPago(StringParser.toDouble(row.get(2).toString()))
                    .quantidade(StringParser.toInteger(row.get(5).toString()))
                    .totalInvestido(StringParser.toDouble(row.get(6).toString()))
                    .dataCompra(dataUtil.dataBrParaLocalDate(row.get(9).toString()))
                    .build();
        }

        return null;
    }
}
