package br.com.lunacom.portal.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum Seguindo {
    NAO("1","Não"),
    MENSALMENTE("2","Mensalmente"),
    QUINZENALMENTE("3","Quinzenalmente"),
    SEMANALMENTE("4","Semanalmente"),
    DIARIAMENTE("5","Diariamente"),
    ;

    private final String codigo;
    private final String descricao;

    public static Seguindo fromCodigo(String value) {
        return EnumSet.allOf(Seguindo.class)
                .stream()
                .filter(it -> it.getCodigo().equals(value))
                .findFirst()
                .orElse(NAO);
    }
}
