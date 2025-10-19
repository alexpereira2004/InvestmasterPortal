package br.com.lunacom.portal.repository.monitor;

import br.com.lunacom.portal.domain.entity.monitor.MonitorRegraQuedaPercentual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitorRegraQuedaPercentualRepository
        extends JpaRepository<MonitorRegraQuedaPercentual, Integer> {
    List<?> findAllByRegraId(Integer id);
}
