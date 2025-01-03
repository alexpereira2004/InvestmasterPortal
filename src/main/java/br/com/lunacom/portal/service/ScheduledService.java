package br.com.lunacom.portal.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduledService {

    private final CotacaoService cotacaoService;

    //    @Scheduled(fixedRate = 5000) // Executa a cada 5 segundos
    @Scheduled(cron = "${app.schedule.googleSheet}")
    public void executeTask() throws IOException {
        log.info("Executando tarefa agendada...");
        cotacaoService.importarDadosGoogle();
    }
}