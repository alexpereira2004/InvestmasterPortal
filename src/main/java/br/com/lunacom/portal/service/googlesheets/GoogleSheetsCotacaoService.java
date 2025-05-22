package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.CotacaoRowConverter;
import br.com.lunacom.portal.converter.googlesheets.GoogleSheetsRowConverter;
import br.com.lunacom.portal.domain.dto.googlesheets.CotacaoDto;
import br.com.lunacom.portal.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service("googlesheets-cotacao")
public class GoogleSheetsCotacaoService
        implements GoogleSheetsDataServiceInterface<CotacaoDto> {

    private final CotacaoRowConverter converter;

    @Override
    public List<CotacaoDto> lerPlanilha(LeituraPlanilhaRequestDto dto) throws IOException {
        final ValueRange valueRange = this.obterDados(dto);

        final List<CotacaoDto> response = convertAll(valueRange.getValues());
        return response;
    }

    @Override
    public GoogleSheetsRowConverter<CotacaoDto> getConverter() {
        return converter;
    }

//    @Override
//    public Sheets getSheetsService() throws IOException {
//        return GoogleSheetsFactory.getSheetsService();
//    }
}
