package br.com.lunacom.portal.repository;

import br.com.lunacom.portal.domain.MovimentoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MovimentoVendaRepository extends JpaRepository<MovimentoVenda, Integer>, JpaSpecificationExecutor<MovimentoVenda> {

    Optional<MovimentoVenda> findFirstByOrderByDataVendaDesc();

    void deleteByDataVendaGreaterThanEqual(LocalDate ultimaVenda);
}
