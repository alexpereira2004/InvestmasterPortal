package br.com.lunacom.portal.service;

import br.com.lunacom.portal.repository.ProjecaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjecaoService {
    final private ProjecaoRepository repository;

    public List<Integer> buscarTodosAnosComProjecao() {
        return repository.searchAllDistinctByAnos();
    }

}
