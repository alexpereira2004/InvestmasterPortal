package br.com.lunacom.portal.repository;

import br.com.lunacom.comum.domain.Ativo;
import br.com.lunacom.comum.domain.MovimentoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovimentoVendaRepository extends JpaRepository<MovimentoVenda, Integer>, JpaSpecificationExecutor<MovimentoVenda> {

    Optional<MovimentoVenda> findFirstByOrderByDataVendaDesc();

    @Modifying
    void deleteByDataVendaGreaterThanEqual(LocalDate ultimaVenda);

    @Query("SELECT DISTINCT mv.ativo FROM MovimentoVenda mv")
    List<Ativo> findDistinctAtivosFromMovimentoVenda();
}
