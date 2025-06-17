package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.ProdutoFinanceiro;
import br.com.lunacom.portal.repository.ProdutoFinanceiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProdutoFinanceiroService {
    private final ProdutoFinanceiroRepository repository;

    public ProdutoFinanceiro pesquisarPorNome(String nome) {
        return repository.findAllByNome(nome);
    }
}
