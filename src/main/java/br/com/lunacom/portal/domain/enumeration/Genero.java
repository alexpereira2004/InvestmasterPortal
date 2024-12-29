package br.com.lunacom.portal.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum Genero {
    MASCULINO("M", "Masculino"),
    FEMININO("F", "Feminino");

    private final String codigo;
    private final String descricao;

    public static Genero fromCodigo(String value) {
        return EnumSet.allOf(Genero.class)
                .stream()
                .filter(it -> it.getCodigo().equals(value))
                .findFirst()
                .orElse(MASCULINO);
    }
}
