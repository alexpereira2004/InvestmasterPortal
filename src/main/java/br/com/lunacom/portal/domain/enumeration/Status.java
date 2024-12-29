package br.com.lunacom.portal.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum Status {
    INATIVO("I","Inativo"),
    ATIVO("A", "Ativo");

    private final String codigo;
    private final String Descricao;

    public static Status fromCodigo(String value) {
        return EnumSet.allOf(Status.class)
                .stream()
                .filter(it -> it.getCodigo().equals(value))
                .findFirst()
                .orElse(ATIVO);
    }
}
