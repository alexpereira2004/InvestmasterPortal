package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.Cotacao;
import br.com.lunacom.portal.domain.dto.GoogleSpreadsheetCotacaoDto;
import br.com.lunacom.portal.repository.CotacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static br.com.lunacom.portal.util.StringParser.toDouble;

@Slf4j
@RequiredArgsConstructor
@Service
public class CotacaoService {

    private final CotacaoRepository repo;
    private final AtivoService ativoService;
    private final GoogleSheetsDataService googleSheetsDataService;

    @Value("${app.googleSheet.spreadsheetId}")
    private String spreadsheetId;

    @Value("${app.googleSheet.range}")
    private String range;

    public void importarDadosGoogle() throws IOException {
        final List<GoogleSpreadsheetCotacaoDto> lists = googleSheetsDataService
                .lerPlanilha(spreadsheetId,range);
        log.info(String.format("A leitura da planilha foi realizada e encontrou %s diferentes cotações", String.valueOf(lists.size())));
        this.salvarCotacoesGoogleSpreadsheet(lists);
    }

    private void salvarCotacoesGoogleSpreadsheet
            (List<GoogleSpreadsheetCotacaoDto> googleCotacoes) {

        final List<Cotacao> cotacaoList = googleCotacoes.stream()
                .map(a -> montarObjetoCotacao(a.getCotacao(), a.getCodigo()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        insertAll(cotacaoList);
    }

    private Cotacao montarObjetoCotacao(String cotacaoLida, String codigoLida) {

        final Ativo ativo = ativoService.pesquisarPorCodigo(codigoLida).orElse(null);
        if (Objects.isNull(ativo)) {
            log.warn(String.format("O código %s precisa ser adicionado ao banco de dados", codigoLida));
            return null;
        }

        final List<Cotacao> listaCotacaoExistente = repo
                .findAllByReferenciaEquals(LocalDate.now());

        final Cotacao cotacao = listaCotacaoExistente.stream()
                .filter(c -> c.getAtivo().getCodigo().equals(codigoLida))
                .findFirst()
                .orElse(new Cotacao());

        cotacao.setImportacao(LocalDateTime.now());
        cotacao.setPreco(toDouble(cotacaoLida));

        if (Objects.isNull(cotacao.getId())) {
            aplicarRegrasParaNovaCotacao(cotacaoLida, ativo, cotacao);
        } else {
            aplicarRegrasParaAtualizarCotacaoExistente(cotacaoLida, cotacao);
        }
        return cotacao;
    }

    private void aplicarRegrasParaNovaCotacao(String cotacaoLida, Ativo ativo, Cotacao cotacao) {
        cotacao.setReferencia(LocalDate.now());
        cotacao.setAtivo(ativo);
        cotacao.setVariacao(0D);
        cotacao.setAbertura(toDouble(cotacaoLida));
        cotacao.setMaxima(toDouble(cotacaoLida));
        cotacao.setMinima(toDouble(cotacaoLida));
        cotacao.setOrigem("GoogleSpreadsheet");
    }

    private void aplicarRegrasParaAtualizarCotacaoExistente(String cotacaoLida, Cotacao cotacao) {
        final Double novaCotacao = toDouble(cotacaoLida);
        final Double maxima = cotacao.getMaxima().compareTo(novaCotacao) > 1 ? cotacao.getMaxima() : novaCotacao;
        cotacao.setMaxima(maxima);
        final Double minima = cotacao.getMinima().compareTo(novaCotacao) > 1 ? novaCotacao : cotacao.getMinima();
        cotacao.setMinima(minima);
        cotacao.setVariacao(toDouble(cotacaoLida) - cotacao.getAbertura());
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
