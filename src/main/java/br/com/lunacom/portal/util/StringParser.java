package br.com.lunacom.portal.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

public class StringParser {

    public static Integer toInteger(String input) {
        try {
            return Optional.ofNullable(input)
                    .map(String::trim)
                    .map(Integer::valueOf)
                    .orElse(null);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    public static Integer toInteger(String input, Integer defaultValue) {
        return Optional.ofNullable(input).map(Integer::valueOf).orElse(defaultValue);
    }

    public static Double toDouble(String input) {
        if (Objects.isNull(input) || input.isEmpty()) {
            return 0D;
        }
        final String valorFormatado = input
                .replaceAll("[^\\d,\\.]", "")
                .replace(".", "")
                .replace(",", ".");
        if (valorFormatado.isEmpty()) {
            return 0D;
        }
        return Double.parseDouble(valorFormatado);
    }

    public static Double toDouble(String input, Double defaultValue) {
        input = input.replace(",",".");
        return Optional.ofNullable(input).map(Double::valueOf).orElse(defaultValue);
    }

    public static String prepareCsvFromRequest(String old) throws UnsupportedEncodingException {
        final String newString = URLDecoder.decode(old, "UTF8");
        return newString.replace("=","");
    }

    public static LocalDate toLocalDate(String dataFormado_dd_MM_yyyy) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(dataFormado_dd_MM_yyyy, formatter);
    }

}
