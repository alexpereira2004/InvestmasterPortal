package br.com.lunacom.portal.service;


import br.com.lunacom.portal.domain.dto.GoogleSpreadsheetCotacaoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduledService {

    private final CotacaoService cotacaoService;
    private final GoogleSheetsDataService googleSheetsDataService;

    @Value("${app.googleSheet.spreadsheetId}")
    private String spreadsheetId;

    @Value("${app.googleSheet.range}")
    private String range;

    @Scheduled(fixedRate = 10000)
//    @Scheduled(cron = "0 0-59 9-21 ? * MON-FRI")
    public void executeTask() {
        log.info("Executando tarefa agendada...");
        try {
            final List<GoogleSpreadsheetCotacaoDto> lists = googleSheetsDataService.lerPlanilha(
                    "1eVEXKZY_Jp-SH0pmweLa9Y1HbOmNimyou3gX14X4YW4",
                    "Cotacoes!A1:B51");
            log.info(lists.get(0).toString());
            cotacaoService.salvarCotacoesGoogleSpreadsheet(lists);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}