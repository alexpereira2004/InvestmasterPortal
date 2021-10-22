package br.com.lunacom.portal.repository;

import br.com.lunacom.portal.domain.Dividendo;

import java.time.LocalDate;
import java.util.List;

public interface DividendoRepository extends GenericRepository<Dividendo> {
    List<Dividendo> findByDataRecebimentoBetween(LocalDate maiorQue, LocalDate menorQue);
}
