package br.com.lunacom.portal.repository;

import br.com.lunacom.portal.domain.entity.monitor.MonitorRegra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonitorRegraRepository extends JpaRepository<MonitorRegra, Integer> {

    @Query("SELECT r FROM MonitorRegra r " +
            "LEFT JOIN FETCH r.regrasQuedaPercentual " +
            "LEFT JOIN FETCH r.regrasVolume " +
            "LEFT JOIN FETCH r.regrasCruzamentoMedia " +
            "WHERE r.id = :id")
    Optional<MonitorRegra> buscarComRegras(@Param("id") Integer id);
}

