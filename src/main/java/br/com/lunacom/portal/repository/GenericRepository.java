package br.com.lunacom.portal.repository;

import br.com.lunacom.comum.domain.GenericEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T extends GenericEntity<T>>
        extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T> {
}
