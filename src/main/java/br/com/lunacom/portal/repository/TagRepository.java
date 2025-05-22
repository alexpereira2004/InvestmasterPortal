package br.com.lunacom.portal.repository;

import br.com.lunacom.portal.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByNomeAndTipo(String nome, String tipo);
}
