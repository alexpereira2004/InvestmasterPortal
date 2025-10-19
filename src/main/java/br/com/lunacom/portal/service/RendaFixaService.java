package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.ProdutoFinanceiro;
import br.com.lunacom.portal.domain.RendaFixa;
import br.com.lunacom.portal.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import br.com.lunacom.portal.domain.dto.googlesheets.RendaFixaDto;
import br.com.lunacom.portal.domain.enumeration.Meses;
import br.com.lunacom.portal.repository.ProdutoFinanceiroRepository;
import br.com.lunacom.portal.repository.RendaFixaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class RendaFixaService {

    private final RendaFixaRepository repository;
    private final ProdutoFinanceiroRepository produtoFinanceiroRepository;
    private final ProdutoFinanceiroService produtoFinanceiroService;

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

    public void compararDadosAtuaisESalvar(LeituraPlanilhaRequestDto dto, List<RendaFixaDto> novosDados) {
        final List<RendaFixa> rendaFixaAtualList = this.pesquisarTodosPorAno(dto.getAno());

        for (int i = 0; i < rendaFixaAtualList.size(); i++) {
            var e = rendaFixaAtualList.get(i);

            String mes = Meses.fromCodigo(e.getDataReferencia().substring(5, 7)).getDescricaoPt();
            String inst = e.getProdutoFinanceiro().getNome();

            novosDados.stream()
                .filter(item -> Objects.equals(item.getData(), mes)
                        && Objects.equals(item.getInstituicao(), inst))
                .findFirst()
                .ifPresent( novoItem -> {
                    e.setRenda(BigDecimal.valueOf(novoItem.getRenda()));
                    e.setAplicado(BigDecimal.valueOf(novoItem.getInvestido()));
                    e.setRentabilidade(BigDecimal.valueOf(novoItem.getRentabilidade()));
                    e.setComparacao(BigDecimal.valueOf(novoItem.getComparaticoComCdi()));
                    e.setReferenciaValor(BigDecimal.valueOf(novoItem.getCdiMes()));
                    e.setComparacaoReferencia("CDI");
                    e.setProdutoFinanceiro(produtoFinanceiroService.pesquisarPorNome(novoItem.getInstituicao()));
                    this.salvar(e);
                });
        }
    }

    public boolean quantidadeProdutosFinanceirosExistenteEhDiferente(
            String ano, int quantidadeDadosNovos) {

        final List<RendaFixa> dadosExistentes = repository.findByDataReferenciaStartingWith(ano);
        if (dadosExistentes.size() == quantidadeDadosNovos) {
            return false;
        }
        return true;
    }

    @Transactional
    public void apagarPorAno(String ano) {
        repository.deleteByAno(ano);
    }


}
