package br.com.lunacom.portal.repository;

import br.com.lunacom.comum.domain.Aporte;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AporteRepository extends GenericRepository<Aporte> {

    Optional<Aporte> findFirstByOrderByDataAporteDesc();

    void deleteByDataAporteGreaterThanEqual(LocalDate ultimoAporte);

}
