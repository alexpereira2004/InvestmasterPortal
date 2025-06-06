package br.com.lunacom.portal.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum TipoContato {

    WHATS("W", "Whatsapp"),
    TELEFONE("T","Telefone"),
    EMAIL("E","Email"),
    OUTRO("O","Telefone");

    private final String codigo;
    private final String Descricao;

    public static TipoContato fromCodigo(String value) {
        return EnumSet.allOf(TipoContato.class)
                .stream()
                .filter(it -> it.getCodigo().equals(value))
                .findFirst()
                .orElse(WHATS);
    }
}
