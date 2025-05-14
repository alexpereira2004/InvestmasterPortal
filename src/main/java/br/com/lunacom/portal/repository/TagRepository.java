package br.com.lunacom.portal.repository;

import br.com.lunacom.portal.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
