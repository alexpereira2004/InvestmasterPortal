package br.com.lunacom.portal.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum Periodicidade {
    DIARIO("D", "Diario"),
    MENSAL("M", "Mensal"),
    ANUAL("A", "Anual");

    private final String codigo;
    private final String descricao;

    public static Periodicidade fromCodigo(String value) {
        return EnumSet.allOf(Periodicidade.class)
                .stream()
                .filter(it -> it.getCodigo().equals(value))
                .findFirst()
                .orElse(DIARIO);
    }
}
