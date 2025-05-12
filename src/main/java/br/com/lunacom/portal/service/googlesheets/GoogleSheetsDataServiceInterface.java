package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.GoogleSheetsRowConverter;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface GoogleSheetsDataServiceInterface <T> {

    GoogleSheetsRowConverter<T> getConverter();

    List<T> lerPlanilha(String spreadsheetId, String range) throws IOException;

    default ValueRange obterDados(String spreadsheetId, String range) throws IOException {
        Sheets sheetsService = getSheetsService();
        return sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
    }

    default List<T> convertAll(List<List<Object>> data) {
        return data.stream()
                .map(getConverter()::convert)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    default Sheets getSheetsService() throws IOException {
        return GoogleSheetsFactory.getSheetsService();
    }
}
