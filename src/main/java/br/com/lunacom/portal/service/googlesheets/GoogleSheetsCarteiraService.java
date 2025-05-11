package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.CarteiraRowConverter;
import br.com.lunacom.portal.converter.googlesheets.GoogleSheetsRowConverter;
import br.com.lunacom.portal.domain.dto.googlesheets.CarteiraDto;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service("googlesheets-carteira")
public class GoogleSheetsCarteiraService
        implements GoogleSheetsDataServiceInterface<CarteiraDto> {

    private final CarteiraRowConverter converter;

    @Override
    public GoogleSheetsRowConverter<CarteiraDto> getConverter() {
        return converter;
    }

    @Override
    public List<CarteiraDto> lerPlanilha(String spreadsheetId, String range) throws IOException {
        final ValueRange valueRange = this.obterDados(spreadsheetId, range);

        final List<CarteiraDto> carteiraDtos = convertAll(valueRange.getValues());

        return carteiraDtos;
    }
}
