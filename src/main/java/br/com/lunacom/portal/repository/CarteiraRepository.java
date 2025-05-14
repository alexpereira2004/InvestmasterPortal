package br.com.lunacom.portal.repository;

import br.com.lunacom.portal.domain.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteiraRepository extends JpaRepository<Carteira, Integer> {
}
