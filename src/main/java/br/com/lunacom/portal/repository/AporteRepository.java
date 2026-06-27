package br.com.lunacom.portal.repository;

import br.com.lunacom.comum.domain.Aporte;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AporteRepository extends GenericRepository<Aporte> {

    Optional<Aporte> findFirstByOrderByDataAporteDesc();

    @Transactional
    void deleteByDataAporteGreaterThanEqual(LocalDate ultimoAporte);

    @Query("SELECT COALESCE(SUM(a.valor), 0) FROM Aporte a WHERE YEAR(a.dataAporte) = :ano")
    BigDecimal sumValorByAno(
            @Param("ano") Integer ano
    );

}
