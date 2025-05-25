package br.com.lunacom.portal.converter.googlesheets;

import br.com.lunacom.portal.domain.dto.googlesheets.VendidasDto;
import br.com.lunacom.portal.util.DataUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static br.com.lunacom.portal.util.StringParser.toDouble;
import static br.com.lunacom.portal.util.StringParser.toInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class VendidasRowConverter implements GoogleSheetsRowConverter<VendidasDto> {

    private final DataUtil dataUtil;

    @Override
    public VendidasDto convert(List<Object> row) {
        if (row == null || row.isEmpty() || row.size() < 1) {
            log.warn("Linha ignorada por estar vazia ou incompleta na conversão de dados da Carteira: {}", row);
            return null;
        }

        if (row.size() == 1) {
            return VendidasDto.builder()
                    .header(check(row, 0))
                    .build();
        }

        Set<Integer> tamanhosValidos = new HashSet<>(Arrays.asList(12, 14));

        if (tamanhosValidos.contains(row.size())) {
            return VendidasDto.builder()
                    .codigoAtivo(check(row, 0))
                    .precoPago(toDouble(row.get(2).toString()))
                    .precoFinal(toDouble(row.get(3).toString()))
                    .rendimento(toDouble(row.get(4).toString()))
                    .quantidade(toInteger(row.get(5).toString()))
                    .totalInvestido(toDouble(row.get(6).toString()))
                    .atualizacao(toDouble(row.get(7).toString()))
                    .diferenca(toDouble(row.get(8).toString()))
                    .dataCompra(dataUtil.dataBrParaLocalDate(row.get(9).toString()))
                    .dataVenda(dataUtil.dataBrParaLocalDate(row.get(10).toString()))
                    .dias(toInteger(row.get(11).toString()))
                    .build();
        }
        return null;
    }
}
