package br.com.lunacom.portal.repository;


import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.Cotacao;
import br.com.lunacom.portal.domain.dto.ExtratoCotacaoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CotacaoRepository extends JpaRepository<Cotacao, Integer> {

    List<Cotacao> findAllByAtivoAndReferenciaGreaterThanEqual(Ativo a, Date d);
    List<Cotacao> findAllByReferenciaEquals(LocalDate d);
    List<Cotacao> findAllByAtivoAndReferenciaAfter(Ativo a, Date d);
    List<Cotacao> findAllByAtivoAndReferencia(Ativo a, Date d);
    List<Cotacao> findAllByAtivoAndReferenciaBetween(Ativo a, Date inicio, Date fim);

    Optional<Cotacao> findTopByAtivoOrderByReferenciaDesc(Ativo integer);

    @Query(nativeQuery = true)
    List<ExtratoCotacaoDto> extrato(List<String> codigoLista, LocalDate dataInicio, LocalDate dataFim);
}
