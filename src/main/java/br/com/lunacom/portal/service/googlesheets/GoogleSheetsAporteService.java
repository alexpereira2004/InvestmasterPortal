package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.GoogleSheetsRowConverter;
import br.com.lunacom.portal.domain.Aporte;
import br.com.lunacom.portal.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("googlesheets-aporte")
public class GoogleSheetsAporteService implements GoogleSheetsDataServiceInterface<Aporte> {

    @Override
    public GoogleSheetsRowConverter<Aporte> getConverter() {
        return null;
    }

    @Override
    public List<Aporte> lerPlanilha(LeituraPlanilhaRequestDto dto) throws IOException {
        return null;
    }
}
