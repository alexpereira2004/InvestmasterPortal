package br.com.lunacom.portal.converter.googlesheets;


import java.util.List;

public interface GoogleSheetsRowConverter<T> {
    T convert(List<Object> row);
}
