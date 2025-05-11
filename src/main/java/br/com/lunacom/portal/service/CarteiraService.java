package br.com.lunacom.portal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class CarteiraService {

    @Value("${app.googleSheet.spreadsheetId}")
    private String spreadsheetId;


    public void importarDadosGoogle() throws IOException {

//        String range = "Teste!A3:D34";
//
//        final List<GoogleSpreadsheetCotacaoDto> lists = googleSheetsDataService
//                .lerPlanilha(spreadsheetId,range);
//        log.info(String.format("A leitura da planilha foi realizada e encontrou %s diferentes cotações", String.valueOf(lists.size())));
//
//        log.info("Leitura e inclusão concluídas");
    }
}
