package br.com.lunacom.portal.repository;

import br.com.lunacom.portal.domain.ResultadoDividendo;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface ResultadoDividendoRepository extends JpaRepository<ResultadoDividendo, String> {

}
