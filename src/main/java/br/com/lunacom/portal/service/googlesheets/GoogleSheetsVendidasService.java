package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.GoogleSheetsRowConverter;
import br.com.lunacom.portal.converter.googlesheets.VendidasRowConverter;
import br.com.lunacom.portal.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import br.com.lunacom.portal.domain.dto.googlesheets.VendidasDto;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service("googlesheets-vendidas")
public class GoogleSheetsVendidasService implements GoogleSheetsDataServiceInterface<VendidasDto> {

    private final VendidasRowConverter converter;

    @Override
    public GoogleSheetsRowConverter<VendidasDto> getConverter() {
        return converter;
    }

    @Override
    public List<VendidasDto> lerPlanilha(LeituraPlanilhaRequestDto dto) throws IOException {
        final ValueRange valueRange = this.obterDados(dto);
        final List<VendidasDto> vendidasDtoList = convertAll(valueRange.getValues());
        return vendidasDtoList;
    }
}
