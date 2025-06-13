package br.com.lunacom.portal.repository;

import br.com.lunacom.portal.domain.MovimentoCompra;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MovimentoCompraRepository extends GenericRepository<MovimentoCompra> {
    Optional<MovimentoCompra> findFirstByOrderByDataAquisicaoDesc();

    void deleteByDataAquisicaoGreaterThanEqual(LocalDate ultimaAquisicao);
}
