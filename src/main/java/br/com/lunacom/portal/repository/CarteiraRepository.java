package br.com.lunacom.portal.repository;

import br.com.lunacom.portal.domain.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarteiraRepository extends JpaRepository<Carteira, Integer> {

    Optional<Carteira> findByAtivoCodigo(String nome);

    void deleteByAtivoCodigo(String codigo);
}
