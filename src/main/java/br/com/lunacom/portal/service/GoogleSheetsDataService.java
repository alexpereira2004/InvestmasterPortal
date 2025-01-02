package br.com.lunacom.portal.service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GoogleSheetsDataService {

    @Autowired
    private GoogleSheetsService googleSheetsService;

    public List<List<Object>> readSpreadsheet(String spreadsheetId, String range) throws IOException {
        Sheets sheetsService = googleSheetsService.getSheetsService();
        ValueRange response = sheetsService.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        return response.getValues();
    }
}