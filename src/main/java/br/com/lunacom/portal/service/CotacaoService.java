package br.com.lunacom.portal.service;

import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.Cotacao;
import br.com.lunacom.portal.repository.CotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CotacaoService {

    @Autowired
    private CotacaoRepository repo;

    @Autowired
    private AtivoService ativoService;

    public Cotacao insert(Cotacao c) {
        c.setImportacao(new Date());
        return repo.save(c);
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
