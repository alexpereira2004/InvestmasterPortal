package br.com.lunacom.portal.repository;

import br.com.lunacom.portal.domain.ProdutoFinanceiro;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProdutoFinanceiroRepository extends GenericRepository<ProdutoFinanceiro> {

    List<ProdutoFinanceiro> findAllByNomeIn(Collection<String> nomes);

}
