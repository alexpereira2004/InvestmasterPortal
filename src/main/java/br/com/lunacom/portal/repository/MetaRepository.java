package br.com.lunacom.portal.repository;

import br.com.lunacom.comum.domain.entity.meta.Meta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetaRepository extends GenericRepository<Meta> {

    Optional<Meta> findAllByCategoriaAndAno(String categoria, Integer ano);

    @Query("SELECT m FROM Meta m " +
            "WHERE m.ano = :ano " +
            "AND LOWER(m.categoria) LIKE LOWER(CONCAT('%', :categoria, '%'))")
    List<Meta> buscarPorAnoECategoriaLike(@Param("ano") Integer ano,
                                          @Param("categoria") String categoria);
}
