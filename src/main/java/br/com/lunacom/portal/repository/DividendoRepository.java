package br.com.lunacom.portal.repository;

import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.Dividendo;
import br.com.lunacom.portal.domain.dto.MediaDividendosValoresDto;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DividendoRepository extends GenericRepository<Dividendo> {
    List<Dividendo> findByDataRecebimentoBetween(LocalDate maiorQue, LocalDate menorQue);

    @Query(nativeQuery = true)
    List<MediaDividendosValoresDto> getMediaDividendosTotal();

    @Query(nativeQuery = true)
    List<MediaDividendosValoresDto> getMediaDividendosAcoes();

    @Query(nativeQuery = true)
    List<MediaDividendosValoresDto> getMediaDividendosFundos();

    @Query(nativeQuery = true)
    List<MediaDividendosValoresDto> getMediaDividendosOutros();

    @Query("SELECT DISTINCT d.ativo FROM Dividendo d")
    List<Ativo> findDistinctAtivos();
}
