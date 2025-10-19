package br.com.lunacom.portal.repository.monitor;

import br.com.lunacom.portal.domain.entity.monitor.MonitorRegraCruzamentoMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitorRegraCruzamentoMediaRepository
        extends JpaRepository<MonitorRegraCruzamentoMedia, Integer> {

    List<MonitorRegraCruzamentoMedia> findAllByRegraId(Integer id);
}
