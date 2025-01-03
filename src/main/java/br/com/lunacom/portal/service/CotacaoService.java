package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.Cotacao;
import br.com.lunacom.portal.domain.dto.GoogleSpreadsheetCotacaoDto;
import br.com.lunacom.portal.repository.CotacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static br.com.lunacom.portal.util.StringParser.toDouble;

@RequiredArgsConstructor
@Service
public class CotacaoService {


    private final CotacaoRepository repo;
    private final AtivoService ativoService;

    public void salvarCotacoesGoogleSpreadsheet
            (List<GoogleSpreadsheetCotacaoDto> googleCotacoes) {

        final String cotacao = googleCotacoes.get(0).getCotacao();
        final String codigo = googleCotacoes.get(0).getCodigo();

        final Ativo ativo = ativoService.pesquisarPorCodigo(codigo)
                .orElseThrow(() -> new NoSuchElementException("Ativo pesquisado não existe " + codigo));

        final List<Cotacao> listaCotacaoExistente = repo.findAllByReferenciaEquals(LocalDate.now());


        final Cotacao cotacao2 = listaCotacaoExistente.stream()
                .filter(c -> c.getAtivo().getCodigo().equals(codigo))
                .findFirst()
                .orElse(new Cotacao());

        cotacao2.setImportacao(LocalDateTime.now());
        cotacao2.setPreco(toDouble(cotacao));

        if (Objects.isNull(cotacao2.getId())) {
            cotacao2.setReferencia(LocalDate.now());
            cotacao2.setAtivo(ativo);
            cotacao2.setAbertura(toDouble(cotacao));
            cotacao2.setMaxima(toDouble(cotacao));
            cotacao2.setMinima(toDouble(cotacao));
            cotacao2.setOrigem("GoogleSpreadsheet");
        } else {
            final Double novaCotacao = toDouble(cotacao);
            final Double maxima = cotacao2.getMaxima().compareTo(novaCotacao) > 1 ? cotacao2.getMaxima() : novaCotacao;
            cotacao2.setMaxima(maxima);
            final Double minima = cotacao2.getMinima().compareTo(novaCotacao) > 1 ? novaCotacao : cotacao2.getMinima();
            cotacao2.setMinima(minima);
        }

        insertAll(Arrays.asList(cotacao2));


    }

    public void insertAll(List<Cotacao> cotacoes) {
        repo.saveAll(cotacoes);
    }

    public List<Cotacao> findAllByAtivoAndReferenciaGreaterThanEqual (Ativo a, Date d) {
        return repo.findAllByAtivoAndReferenciaGreaterThanEqual(a, d);
    }
    public List<Cotacao> findAllByAtivoAndReferenciaAfter(Ativo a, Date d) {

        return repo.findAllByAtivoAndReferenciaAfter(a, d);
    }

    public List<Cotacao> findAllByAtivoAndReferencia(Ativo a, Date d) {
        return repo.findAllByAtivoAndReferencia(a, d);
    }

//    public List<CotacaoAtivoDto> find(String codigoAtivo, Date datainicial, Date datafinal) throws ObjectNotFoundException {
//        final Ativo ativo = ativoService.searchAtivoByCodigo(codigoAtivo);
//        final List<Cotacao> cotacoes = repo.findAllByAtivoAndReferenciaBetween(ativo, datainicial, datafinal);
//        final List<CotacaoAtivoDto> cotacaoAtivoDtoList = cotacoes.stream().map(Cotacao::toCotacaoAtivoDto).collect(Collectors.toList());
//        Collections.sort(cotacaoAtivoDtoList, Collections.reverseOrder());
//        return cotacaoAtivoDtoList;
//    }

    public Cotacao buscarCotacaoMaisRecente(Ativo ativo) {
        Optional<Cotacao> optionalCotacao = repo.findTopByAtivoOrderByReferenciaDesc(ativo);
        return optionalCotacao
                .orElseThrow(
                        () -> new NoSuchElementException("Cotação não existe para o "+ ativo.getCodigo()));
    }
}
