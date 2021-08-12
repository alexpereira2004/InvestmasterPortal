package br.com.lunacom.portal.repository;

import br.com.lunacom.portal.domain.MovimentoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentoCompraRepository extends JpaRepository<MovimentoCompra, Integer> {
}
