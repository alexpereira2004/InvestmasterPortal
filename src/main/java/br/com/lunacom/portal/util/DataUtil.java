package br.com.lunacom.portal.util;

import br.com.lunacom.portal.domain.enumeration.Meses;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
@NoArgsConstructor
public class DataUtil {
    private static String ANO_MES_DIA = "yyyyMMdd";
    private static String ANO_MES_DIA_HIFEN = "yyyy-MM-dd";
    private static String DIA_MES_ANO_BARRA = "dd/MM/yyyy";

    public String localDateParaDataBr(LocalDate origem) {
        if (Objects.isNull(origem)) {
            return "";
        }
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DIA_MES_ANO_BARRA);
        return formatter.format(origem);
    }

    public LocalDate dataBrParaLocalDate(String origem) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DIA_MES_ANO_BARRA);
        return LocalDate.parse(origem, formatter);
    }

    public LocalDate dataEmExtensoParaLocalDate(String origem) {
        String[] split = origem.split("DE ");
        final Integer dia = Integer.valueOf(split[0].trim());
        final Integer mes = Integer.valueOf(Meses.fromDescricao(split[1].trim().substring(0, 1).toUpperCase() + split[1].toLowerCase().trim().substring(1)).getCodigo());
        final Integer ano = Integer.valueOf(split[2].trim());
        return LocalDate.of(ano, mes, dia);
    }

    public LocalDate dataAgora() {
        return LocalDate.now();
    }

    public LocalDateTime dataHoraAora() {
        return LocalDateTime.now();
    }
}
