package br.com.lunacom.portal.repository.monitor;

import br.com.lunacom.comum.domain.entity.monitor.RegraCompraPorHistoricoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RegraCompraPorHistoricoVendaRepository
        extends JpaRepository<RegraCompraPorHistoricoVenda, Integer>,
                JpaSpecificationExecutor<RegraCompraPorHistoricoVenda> {

    @Modifying
    @Transactional
    @Query("UPDATE RegraCompraPorHistoricoVenda r SET r.status = 'I' WHERE r.status = 'A' AND r.monitor.id = :monitorId")
    int inativarRegrasPorMonitor(@Param("monitorId") Integer monitorId);
}
