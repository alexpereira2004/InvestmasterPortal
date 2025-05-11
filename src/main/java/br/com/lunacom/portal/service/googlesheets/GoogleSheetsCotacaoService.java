package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.CotacaoRowConverter;
import br.com.lunacom.portal.converter.googlesheets.GoogleSheetsRowConverter;
import br.com.lunacom.portal.domain.dto.GoogleSpreadsheetCotacaoDto;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service("google-sheets-cotacao")
public class GoogleSheetsCotacaoService implements GoogleSheetsDataServiceInterface<GoogleSpreadsheetCotacaoDto> {

    private final CotacaoRowConverter converter;

    @Override
    public List<GoogleSpreadsheetCotacaoDto> lerPlanilha(String spreadsheetId, String range) throws IOException {
        final ValueRange valueRange = this.obterDados(spreadsheetId, range);

        final List<GoogleSpreadsheetCotacaoDto> response = convertAll(valueRange.getValues());
        return response;
    }

    @Override
    public GoogleSheetsRowConverter<GoogleSpreadsheetCotacaoDto> getConverter() {
        return converter;
    }

    @Override
    public Sheets getSheetsService() throws IOException {
        return GoogleSheetsFactory.getSheetsService();
    }
}
