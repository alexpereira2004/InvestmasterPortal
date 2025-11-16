package br.com.lunacom.portal.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum TipoMovimento {
    COMPRA("C", "Compra"),
    VENDA("R","Venda"),
    AMBOS("A","Ambos");

    private final String codigo;
    private final String Descricao;

    public static TipoMovimento fromCodigo(String value) {
        return EnumSet.allOf(TipoMovimento.class)
                .stream()
                .filter(it -> it.getCodigo().equals(value))
                .findFirst()
                .orElse(TipoMovimento.COMPRA);
    }
}
