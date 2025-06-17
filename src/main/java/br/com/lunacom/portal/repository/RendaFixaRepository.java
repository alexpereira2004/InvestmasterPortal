package br.com.lunacom.portal.repository;

import br.com.lunacom.portal.domain.RendaFixa;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RendaFixaRepository extends GenericRepository<RendaFixa> {

    List<RendaFixa> findByDataReferenciaStartingWith(String ano);
}
