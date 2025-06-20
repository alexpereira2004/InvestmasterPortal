package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.AporteRowConverter;
import br.com.lunacom.portal.converter.googlesheets.GoogleSheetsRowConverter;
import br.com.lunacom.portal.domain.AporteDto;
import br.com.lunacom.portal.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("googlesheets-aporte")
public class GoogleSheetsAporteService implements GoogleSheetsDataServiceInterface<AporteDto> {

    private final AporteRowConverter converter;

    @Override
    public GoogleSheetsRowConverter<AporteDto> getConverter() {
        return converter;
    }

    @Override
    public List<AporteDto> lerPlanilha(LeituraPlanilhaRequestDto dto) throws IOException {
        final ValueRange valueRange = this.obterDados(dto);
        final List<AporteDto> rowList = convertAll(valueRange.getValues());

        return null;
    }
}
