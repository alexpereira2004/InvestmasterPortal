package br.com.lunacom.portal.converter.googlesheets;


import java.util.List;

public interface GoogleSheetsRowConverter<T> {
    T convert(List<Object> row);

    default String check(List<Object> o, int pos) {
        if (o == null || pos >= o.size() || o.get(pos) == null) {
            return "";
        }
        return o.get(pos).toString();
    }
}
