package br.com.lunacom.portal.repository;

import br.com.lunacom.portal.domain.AgendamentoConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgendamentoConfigRepository extends JpaRepository<AgendamentoConfig, Integer> {
    Optional<AgendamentoConfig> findByNome(String nome);
}
