package br.com.lunacom.portal.repository;

import br.com.lunacom.portal.domain.Ativo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtivoRepository extends GenericRepository<Ativo> {

    public Optional<Ativo> findByNome(String nome);
    public Optional<Ativo> findByCodigo(String codigo);
    public Optional<Ativo> findFirstByNomeIgnoreCaseAndTipoContains(String nome, String tipo);
    public Optional<List<Ativo>> findAllByCotacoesIsNotNull();
    public Optional<List<Ativo>> findDistinctByCotacoesIsNotNull();
}
