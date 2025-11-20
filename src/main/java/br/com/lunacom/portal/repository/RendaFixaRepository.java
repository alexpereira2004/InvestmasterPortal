package br.com.lunacom.portal.repository;

import br.com.lunacom.comum.domain.RendaFixa;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RendaFixaRepository extends GenericRepository<RendaFixa> {

    List<RendaFixa> findByDataReferenciaStartingWith(String ano);

    @Modifying
    @Query("DELETE FROM RendaFixa r WHERE r.dataReferencia LIKE CONCAT(:ano, '%')")
    void deleteByAno(@Param("ano") String ano);
}
