package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.comum.domain.AgendamentoConfig;
import br.com.lunacom.comum.domain.enumeration.Status;
import br.com.lunacom.portal.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import br.com.lunacom.portal.repository.AgendamentoConfigRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public abstract class TarefaBase {

    protected final AgendamentoConfigRepository agendamentoConfigRepository;
    protected String spreadsheetId;
    protected String range;

    protected Runnable criarTarefaBase(AgendamentoConfig config, GoogleSheetsDataServiceInterface service) {
        return () -> {
            log.info("Executando job '{}' às {}", config.getNome(), LocalTime.now());

            final Optional<AgendamentoConfig> byNome = agendamentoConfigRepository
                    .findByNome(config.getNome());

            Status status = byNome.orElseThrow(()-> new RuntimeException
                    (String.format("Erro ao montar a Runnable criarTask"))).getStatus();

            if (status.equals(Status.ATIVO)) {
                LeituraPlanilhaRequestDto dto = LeituraPlanilhaRequestDto.builder()
                        .spreadsheetId(spreadsheetId)
                        .range(range)
                        .save(true)
                        .ano("2025")
                        .build();

                try {
                    service.lerPlanilha(dto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                log.info("Job '{}' está inativo", config.getNome());
            }
        };
    }

    public void setSpreadsheetId(String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
    }

    public void setRange(String range) {
        this.range = range;
    }
}

