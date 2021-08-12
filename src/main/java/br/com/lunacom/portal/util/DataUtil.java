package br.com.lunacom.portal.util;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
}
