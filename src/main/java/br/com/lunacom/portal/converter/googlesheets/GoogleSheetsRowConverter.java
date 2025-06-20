package br.com.lunacom.portal.converter.googlesheets;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface GoogleSheetsRowConverter<T> {
    T convert(List<Object> row);

    default String check(List<Object> o, int pos) {
        if (o == null || pos >= o.size() || o.get(pos) == null) {
            return "";
        }
        return o.get(pos).toString();
    }

    default boolean tamanhoNaoPermitido(List<Object> row, List<Integer> listaTamanhosNaoPermitidos) {
        Set<Integer> tamanhosInvalidos = new HashSet<>(listaTamanhosNaoPermitidos);
        return tamanhosInvalidos.contains(row.size());
    }


    default boolean linhaVazia(List<Object> row) {
        return row == null || row.isEmpty() || row.size() < 1;
    }
}
