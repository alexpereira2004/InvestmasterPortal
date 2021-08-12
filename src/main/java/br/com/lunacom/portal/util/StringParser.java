package br.com.lunacom.portal.util;

import java.util.Optional;

public class StringParser {

    public static Integer toInteger(String input) {
        return Optional.ofNullable(input).map(Integer::valueOf).orElse(null);
    }
    public static Integer toInteger(String input, Integer defaultValue) {
        return Optional.ofNullable(input).map(Integer::valueOf).orElse(defaultValue);
    }

    public static Double toDouble(String input) {
        return Optional.ofNullable(input).map(Double::valueOf).orElse(null);
    }
    public static Double toDouble(String input, Double defaultValue) {
        return Optional.ofNullable(input).map(Double::valueOf).orElse(defaultValue);
    }

}
