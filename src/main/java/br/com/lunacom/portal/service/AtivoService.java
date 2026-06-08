package br.com.lunacom.portal.service;

import br.com.lunacom.comum.domain.Ativo;
import br.com.lunacom.portal.repository.AtivoRepository;
import br.com.lunacom.portal.repository.DividendoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AtivoService {

    final private AtivoRepository repository;
    final private DividendoRepository dividendoRepository;

    public Optional<Ativo> pesquisarPorCodigo(String codigo) {
        return repository.findByCodigo(codigo);
    }

    public List<Ativo> pesquisarAtivosComDividendos() {
        return dividendoRepository.findDistinctAtivos();
    }

    @Transactional
    public Ativo salvar(Ativo ativo) {
        return repository.save(ativo);
    }
}
