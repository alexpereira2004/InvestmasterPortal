package br.com.lunacom.portal.repository;

import br.com.lunacom.comum.domain.entity.meta.Meta;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetaRepository extends GenericRepository<Meta> {

    Optional<Meta> findAllByCategoriaAndAno(String categoria, Integer ano);
}
