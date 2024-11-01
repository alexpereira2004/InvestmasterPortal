package br.com.lunacom.portal.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;

public class StringParser {

    public static Integer toInteger(String input) {
        return Optional.ofNullable(input).map(Integer::valueOf).orElse(null);
    }
    public static Integer toInteger(String input, Integer defaultValue) {
        return Optional.ofNullable(input).map(Integer::valueOf).orElse(defaultValue);
    }

    public static Double toDouble(String input) {
        int ultimoPonto = input.lastIndexOf('.');
        String parteMilhar = input.substring(0, ultimoPonto).replace(".", "");
        String valorFormatado = parteMilhar + input.substring(ultimoPonto);
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

}
