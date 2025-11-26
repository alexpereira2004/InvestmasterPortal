package br.com.lunacom.portal.repository;

import br.com.lunacom.comum.domain.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CarteiraRepository extends JpaRepository<Carteira, Integer> {

    Optional<Carteira> findByAtivoCodigo(String nome);

    @Transactional
    void deleteByAtivoCodigo(String codigo);

}
