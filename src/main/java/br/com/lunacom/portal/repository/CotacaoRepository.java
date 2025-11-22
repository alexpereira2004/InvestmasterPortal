package br.com.lunacom.portal.repository;


import br.com.lunacom.comum.domain.Ativo;
import br.com.lunacom.comum.domain.Cotacao;
import br.com.lunacom.comum.domain.dto.CotacaoAgoraDto;
import br.com.lunacom.comum.domain.dto.CotacaoHistoricoDto;
import br.com.lunacom.comum.domain.dto.ExtratoCotacaoDto;
import br.com.lunacom.comum.domain.dto.ReferenciaRangeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(nativeQuery = true)
    List<CotacaoAgoraDto> pesquisarCotacaoAgora();

    @Query(nativeQuery = true)
    List<CotacaoHistoricoDto> pesquisarHistorico(
            LocalDate dataInicio,
            LocalDate dataFinal,
            String tipoAtivo,
            List<String> ativos);

    @Query(" SELECT new br.com.lunacom.comum.domain.dto.ReferenciaRangeDto(\n" +
            "        MIN(h.referencia),\n" +
            "        MAX(h.referencia)\n" +
            "    ) " +
            " FROM Cotacao h " +
            " WHERE h.ativo.codigo = :codigo ")
    ReferenciaRangeDto findMinAndMaxReferenciaByAtivo(@Param("codigo") String codigo);

}
