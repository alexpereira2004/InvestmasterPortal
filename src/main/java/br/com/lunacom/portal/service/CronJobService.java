package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.AgendamentoConfig;
import br.com.lunacom.portal.repository.AgendamentoConfigRepository;
import br.com.lunacom.portal.service.googlesheets.GoogleSheetsDataServiceInterface;
import br.com.lunacom.portal.service.googlesheets.ServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@RequiredArgsConstructor
@Service
public class CronJobService {

    private final TaskScheduler taskScheduler;
    private final AgendamentoConfigRepository configRepository;
    private final ServiceFactory factory;
    private final Map<Integer, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    @PostConstruct
    public void iniciarAgendamentos() {
        List<AgendamentoConfig> configs = configRepository.findAll();

        for (AgendamentoConfig config : configs) {
            GoogleSheetsDataServiceInterface<?> service = factory
                    .getService(config.getNome());
            Runnable task = service.criarTask(config);

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
}
