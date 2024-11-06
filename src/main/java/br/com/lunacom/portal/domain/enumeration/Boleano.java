package br.com.lunacom.portal.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum Boleano {
    SIM("S", "Sim"),
    NAO("N","Não");

    private final String codigo;
    private final String Descricao;

    public static Boleano fromCodigo(String value) {
        return EnumSet.allOf(Boleano.class)
                .stream()
                .filter(it -> it.getCodigo().equals(value))
                .findFirst()
                .orElse(SIM);
    }
}
