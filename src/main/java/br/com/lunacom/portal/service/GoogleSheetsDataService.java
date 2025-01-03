package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.dto.GoogleSpreadsheetCotacaoDto;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleSheetsDataService {

    @Autowired
    private GoogleSheetsService googleSheetsService;

    public List<GoogleSpreadsheetCotacaoDto> lerPlanilha(String spreadsheetId, String range) throws IOException {
        Sheets sheetsService = googleSheetsService.getSheetsService();
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        return convertAll(response.getValues());
    }

    public List<GoogleSpreadsheetCotacaoDto> convertAll(List<List<Object>> data) {
        return data.stream()
                .map(this::convert) // Chama o método de conversão
                .collect(Collectors.toList()); // Coleta como uma lista de DTOs
    }

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