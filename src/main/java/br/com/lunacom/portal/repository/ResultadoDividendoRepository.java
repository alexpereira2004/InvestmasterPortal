package br.com.lunacom.portal.repository;

import br.com.lunacom.comum.domain.ResultadoDividendo;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface ResultadoDividendoRepository extends JpaRepository<ResultadoDividendo, String> {

}
