package br.com.lunacom.portal.repository.monitor;

import br.com.lunacom.portal.domain.entity.monitor.RegraCompraPorHistoricoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegraCompraPorHistoricoVendaRepository
        extends JpaRepository<RegraCompraPorHistoricoVenda, Integer> {
}
