package br.com.lunacom.portal.converter.googlesheets;

import br.com.lunacom.portal.domain.dto.GoogleSpreadsheetCotacaoDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CotacaoRowConverter implements GoogleSheetsRowConverter<GoogleSpreadsheetCotacaoDto> {

    @Override
    public GoogleSpreadsheetCotacaoDto convert(List<Object> data) {
        if (data == null || data.isEmpty() || data.size() < 2) {
            throw new IllegalArgumentException("A lista de dados está vazia ou inválida.");
        }

        return GoogleSpreadsheetCotacaoDto.builder()
                .codigo(data.get(0).toString())
                .cotacao(data.get(1).toString())
                .build();
    }
}
