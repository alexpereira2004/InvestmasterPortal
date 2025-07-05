package br.com.lunacom.portal.service;

import br.com.lunacom.portal.converter.CotacaoLoteSiteInvestingComRequestConverter;
import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.Cotacao;
import br.com.lunacom.portal.domain.CotacaoAgoraDto;
import br.com.lunacom.portal.domain.dto.ExtratoCotacaoDto;
import br.com.lunacom.portal.domain.dto.googlesheets.CotacaoDto;
import br.com.lunacom.portal.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import br.com.lunacom.portal.domain.request.CotacaoLoteSiteInvestingComRequest;
import br.com.lunacom.portal.domain.request.ExtratoCotacaoRequest;
import br.com.lunacom.portal.domain.wrapper.ExtratoCotacaoWrapper;
import br.com.lunacom.portal.repository.CotacaoRepository;
import br.com.lunacom.portal.service.googlesheets.GoogleSheetsDataServiceInterface;
import br.com.lunacom.portal.service.googlesheets.ServiceFactory;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
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
    private final CotacaoLoteSiteInvestingComRequestConverter cotacaoLoteSiteInvestingComRequestConverter;
    private final ServiceFactory factory;

    @Value("${app.googleSheet.spreadsheetId}")
    private String spreadsheetId;
    private String RANGE = "Cotacoes!A2:B77";

    public void importarDadosGoogle() throws IOException {

        final GoogleSheetsDataServiceInterface<CotacaoDto> service = factory
                .getService("googlesheets-cotacao");
        final LeituraPlanilhaRequestDto dto = LeituraPlanilhaRequestDto.builder()
                 .spreadsheetId(spreadsheetId)
                 .range(RANGE)
                .build();
        final List<CotacaoDto> lists = service.lerPlanilha(dto);
        log.info(String.format("A leitura da planilha foi realizada e encontrou %s diferentes cotações", String.valueOf(lists.size())));
        this.salvarCotacoesGoogleSpreadsheet(lists);
        log.info("Leitura e inclusão concluídas");
    }

    public ExtratoCotacaoWrapper gerarExtratoDeUmAtivo(ExtratoCotacaoRequest request) {
        final List<ExtratoCotacaoDto> extratoLista = repo.extrato(
                request.getCodigos(),
                request.getDataInicio(),
                request.getDataFim());

        Map<String, List<ExtratoCotacaoDto>> cotacoes = extratoLista.stream()
                .collect(Collectors.groupingBy(ExtratoCotacaoDto::getCodigo));

        return new ExtratoCotacaoWrapper(cotacoes);
    }

    public void salvarCotacoesGoogleSpreadsheet
            (List<CotacaoDto> googleCotacoes) {

        String padraoReais = "^\\d{1,3}(\\.?\\d{3})*(,\\d{1,2})?$";

        final List<Cotacao> cotacaoList = googleCotacoes.stream()
                .filter(c -> c.getCotacao().matches(padraoReais))
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

    public Boolean importarLoteSiteInvestingCom(String ativoStr, String request) {
        final Reader reader = new StringReader(request);
        CsvToBean<CotacaoLoteSiteInvestingComRequest> csvToBean = new CsvToBeanBuilder(reader)
                .withType(CotacaoLoteSiteInvestingComRequest.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        final Ativo ativo = ativoService.pesquisarPorCodigo(ativoStr).orElse(null);
        if (Objects.isNull(ativo)) {
            log.warn(String.format("O código %s precisa ser adicionado ao banco de dados", ativoStr));
            return null;
        }

        final List<CotacaoLoteSiteInvestingComRequest> parse = csvToBean.parse();
        parse.stream().forEach(e -> e.setAtivo(ativo));
        final List<Cotacao> cotacaoList = cotacaoLoteSiteInvestingComRequestConverter
                .encode(parse);
        final List<Cotacao> response = repo.saveAll(cotacaoList);
        return cotacaoList.size() == response.size();
    }

    public List<CotacaoAgoraDto> pesquisarCotacaoAgora() {
        return repo.pesquisarCotacaoAgora();
    }
}
