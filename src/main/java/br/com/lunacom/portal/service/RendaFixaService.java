package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.ProdutoFinanceiro;
import br.com.lunacom.portal.domain.RendaFixa;
import br.com.lunacom.portal.domain.enumeration.Meses;
import br.com.lunacom.portal.repository.ProdutoFinanceiroRepository;
import br.com.lunacom.portal.repository.RendaFixaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RendaFixaService {

    private final RendaFixaRepository repository;
    private final ProdutoFinanceiroRepository produtoFinanceiroRepository;

    public List<RendaFixa> pesquisarTodosPorAno(String ano) {
        return repository.findByDataReferenciaStartingWith(ano);
    }

    public boolean dadosRendaFixaDoAnoNaoExistem(String ano) {
        if (Objects.isNull(ano)) {
            return false;
        }
        final List<RendaFixa> resultado = repository.findByDataReferenciaStartingWith(ano);
        return resultado.isEmpty();
    }

    public void montarTabelaAno(String ano, Set<String> codigosProdutosFinanceiros) {
        final List<ProdutoFinanceiro> produtoFinanceiroList = produtoFinanceiroRepository.findAllByNomeIn(codigosProdutosFinanceiros);
        Arrays.stream(Meses.values())
                .forEach(mes -> {
                    this.montarTabelaMes(ano, mes, produtoFinanceiroList);
                });
    }

    private void montarTabelaMes(String ano, Meses mes, List<ProdutoFinanceiro> produtosFinanceiros) {
        produtosFinanceiros.stream()
                .forEach(p -> {
                        RendaFixa s = RendaFixa
                            .builder()
                            .dataReferencia(ano + "-" + mes.getCodigo())
                            .produtoFinanceiro(p)
                            .build();
                        repository.save(s);
                });
    }

    public void salvar(RendaFixa e) {
        repository.save(e);
    }
}
