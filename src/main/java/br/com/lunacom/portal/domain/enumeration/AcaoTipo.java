package br.com.lunacom.portal.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum AcaoTipo {
    ACAO("A", "Ação"),
    BDR("B", "BDR"),
    FII("F", "Fundo Imobiliário"),
    TOD("T", "Todos");

    String codigo;
    String Descricao;

    public static AcaoTipo fromCodigo(String value) {
        return EnumSet.allOf(AcaoTipo.class)
                .stream()
                .filter(it -> it.getCodigo().equals(value))
                .findFirst()
                .orElse(ACAO);
    }
}
