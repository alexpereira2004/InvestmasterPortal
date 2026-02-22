package br.com.lunacom.portal.repository;

import br.com.lunacom.comum.domain.Aporte;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AporteRepository extends GenericRepository<Aporte> {

    Optional<Aporte> findFirstByOrderByDataAporteDesc();

    @Transactional
    void deleteByDataAporteGreaterThanEqual(LocalDate ultimoAporte);

}
