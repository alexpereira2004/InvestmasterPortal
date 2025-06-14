package br.com.lunacom.portal.repository;

import br.com.lunacom.portal.domain.MovimentoCompra;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimentoCompraRepository extends GenericRepository<MovimentoCompra> {

    @Query("SELECT m FROM MovimentoCompra m WHERE m.ativo.tipo = :tipo ORDER BY m.dataAquisicao DESC")
    List<MovimentoCompra> findUltimaCompraPorTipo(@Param("tipo") String tipo, Pageable pageable);


    @Query(" SELECT m FROM MovimentoCompra m " +
            " WHERE m.ativo.tipo = :tipo " +
            "   AND m.dataAquisicao >= :ultimaAquisicao ")
    List<MovimentoCompra> findUltimasComprasPorTipoEData (
            @Param("ultimaAquisicao") LocalDate ultimaAquisicao,
            @Param("tipo") String tipo);

}
