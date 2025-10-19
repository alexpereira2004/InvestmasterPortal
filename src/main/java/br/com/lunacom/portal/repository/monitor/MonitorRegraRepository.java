package br.com.lunacom.portal.repository.monitor;

import br.com.lunacom.portal.domain.entity.monitor.MonitorRegra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorRegraRepository extends JpaRepository<MonitorRegra, Integer> {

}

