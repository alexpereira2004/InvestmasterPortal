package br.com.lunacom.portal.repository;

import br.com.lunacom.comum.domain.Projecao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjecaoRepository extends GenericRepository<Projecao>{

    @Query("SELECT DISTINCT p.ano FROM Projecao p ORDER BY p.ano DESC")
    List<Integer> searchAllDistinctByAnos();
}
