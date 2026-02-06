package br.com.lunacom.portal.service;

import br.com.lunacom.comum.domain.AgendamentoConfig;
import br.com.lunacom.comum.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import br.com.lunacom.comum.domain.enumeration.Status;
import br.com.lunacom.portal.repository.AgendamentoConfigRepository;
import br.com.lunacom.portal.service.googlesheets.GoogleSheetsDataServiceInterface;
import br.com.lunacom.portal.service.googlesheets.ServiceFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class CronJobService {

    private final TaskScheduler taskScheduler;
    private final AgendamentoConfigRepository configRepository;
    private final ServiceFactory factory;
    private final Map<Integer, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();
    private final AgendamentoConfigRepository agendamentoConfigRepository;

    @PostConstruct
    public void iniciarAgendamentos() {
        List<AgendamentoConfig> configs = configRepository.findAll();

        for (AgendamentoConfig config : configs) {
            GoogleSheetsDataServiceInterface<?> service = factory
                    .getService(config.getNome());
            Runnable task = criarTarefaBase(config, service);

            CronTrigger trigger = new CronTrigger(config.getCron());


            ScheduledFuture<?> future = taskScheduler.schedule(task, trigger);
            scheduledTasks.put(config.getId(), future);
        }
    }



    public void pararAgendamento(Integer id) {
        ScheduledFuture<?> future = scheduledTasks.get(id);
        if (future != null) {
            future.cancel(true);
            scheduledTasks.remove(id);
        }
    }

    public void reiniciarAgendamento(Integer id) {
//        pararAgendamento(id);
//        configRepository.findById(id).ifPresent(config -> {
//            Runnable task = criarTask(config.getNome());
//            CronTrigger trigger = new CronTrigger(config.getCron());
//            ScheduledFuture<?> future = taskScheduler.schedule(task, trigger);
//            scheduledTasks.put(id, future);
//        });
    }

    private Runnable criarTarefaBase(AgendamentoConfig config, GoogleSheetsDataServiceInterface service) {
        return () -> {
            log.info("Executando job '{}' às {}", config.getNome(), LocalTime.now());

            final Optional<AgendamentoConfig> byNome = agendamentoConfigRepository
                    .findByNome(config.getNome());

            Status status = byNome.orElseThrow(()-> new RuntimeException
                    (String.format("Erro ao montar a Runnable criarTask"))).getStatus();

            if (status.equals(Status.ATIVO)) {
                LeituraPlanilhaRequestDto dto = LeituraPlanilhaRequestDto.builder()
                        .spreadsheetId(service.getSpreadsheetId())
                        .range(service.getRange())
                        .save(true)
                        .ano(String.valueOf(LocalDate.now().getYear()))
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
}
