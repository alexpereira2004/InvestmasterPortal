package br.com.lunacom.portal.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum FormatosData {
    DD_MM_YYYY_PONTO("", "dd.MM.yyyy"),
    DD_MM_YYYY_BARRA("","dd/MM/yyyy");

    private final String codigo;
    private final String Descricao;

    public static FormatosData fromCodigo(String value) {
        return EnumSet.allOf(FormatosData.class)
                .stream()
                .filter(it -> it.getCodigo().equals(value))
                .findFirst()
                .orElse(DD_MM_YYYY_BARRA);
    }
}
