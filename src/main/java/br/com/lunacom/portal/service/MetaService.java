package br.com.lunacom.portal.service;

import br.com.lunacom.comum.domain.entity.meta.Meta;
import br.com.lunacom.portal.repository.MetaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.lunacom.portal.util.MonitorConstants.META_NAO_ENCONTRADA;

@RequiredArgsConstructor
@Service
public class MetaService {
    private final MetaRepository repository;

    public Optional<Meta> pesquisarPorId(Integer id) {
        return repository.findById(id);
    }

    public Meta pesquisarUnicoPorCategoriaEAno(
            String categoria, Integer ano) {
        final Optional<Meta> optional = repository
                .findAllByCategoriaAndAno(categoria, ano);
        final Meta meta = optional.orElseThrow(() -> new EntityNotFoundException(META_NAO_ENCONTRADA));
        return meta;
    }
}
