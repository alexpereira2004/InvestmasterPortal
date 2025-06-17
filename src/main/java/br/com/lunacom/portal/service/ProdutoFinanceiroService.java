package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.ProdutoFinanceiro;
import br.com.lunacom.portal.repository.ProdutoFinanceiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProdutoFinanceiroService {
    private final ProdutoFinanceiroRepository repository;

    private final Map<String, ProdutoFinanceiro> cacheLocal = new HashMap<>();

    public ProdutoFinanceiro pesquisarPorNome(String nome) {
        if (cacheLocal.containsKey(nome)) {
            return cacheLocal.get(nome);
        }
        ProdutoFinanceiro produto = repository.findAllByNome(nome);

        if (produto != null) {
            cacheLocal.put(nome, produto);
        }

        return produto;
    }
}
