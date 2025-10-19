package br.com.lunacom.portal.repository.monitor;

import br.com.lunacom.portal.domain.entity.monitor.MonitorRegraVolume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitorRegraVolumeRepository
        extends JpaRepository<MonitorRegraVolume, Integer> {

    List<?> findAllByRegraId(Integer id);
}
