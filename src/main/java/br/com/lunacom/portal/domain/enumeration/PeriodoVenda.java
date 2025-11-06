package br.com.lunacom.portal.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum PeriodoVenda {
    VENDA_ESPECIFICA("E", "Venda Específica"),
    ULTIMA_VENDA("U", "Última Venda"),
    ANO_ATUAL("A", "Ano Atual"),
    ULTIMOS_12_MESES("D", "Últimos 12 Meses"),
    TODO_HISTORICO("T", "Todo Histórico");

    private final String codigo;
    private final String descricao;

    public static PeriodoVenda fromCodigo(String value) {
        return EnumSet.allOf(PeriodoVenda.class)
                .stream()
                .filter(it -> it.getCodigo().equals(value))
                .findFirst()
                .orElse(ULTIMA_VENDA);
    }
}
