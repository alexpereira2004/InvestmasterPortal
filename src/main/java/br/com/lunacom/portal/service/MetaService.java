package br.com.lunacom.portal.service;

import br.com.lunacom.comum.domain.entity.meta.Meta;
import br.com.lunacom.portal.repository.MetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MetaService {
    private final MetaRepository repository;


    public Optional<Meta> pesquisarPorId(Integer id) {
        return repository.findById(id);
    }
}
