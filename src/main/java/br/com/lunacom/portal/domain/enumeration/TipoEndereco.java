package br.com.lunacom.portal.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum TipoEndereco {
    COMERCIAL("C", "Comercial"),
    RESIDENCIAL("R","Residencial");

    String codigo;
    String Descricao;

    public static TipoEndereco fromCodigo(String value) {
        return EnumSet.allOf(TipoEndereco.class)
                .stream()
                .filter(it -> it.getCodigo().equals(value))
                .findFirst()
                .orElse(RESIDENCIAL);
    }
}