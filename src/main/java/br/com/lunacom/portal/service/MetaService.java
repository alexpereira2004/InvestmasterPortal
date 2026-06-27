package br.com.lunacom.portal.service;

import br.com.lunacom.comum.domain.entity.meta.Meta;
import br.com.lunacom.portal.repository.MetaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import static br.com.lunacom.portal.util.MonitorConstants.META_NAO_ENCONTRADA;

@Slf4j
@RequiredArgsConstructor
@Service
public class MetaService {
    public static final String MSG_VALOR_META_ATUALIZADO = "O valor da meta {} para o ano {} foi atualizada para {}.";
    private final MetaRepository repository;
    private final AporteService aporteService;

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

    public boolean atualizarMetaEspecifica(String categoria, Integer ano) {
        return repository.findAllByCategoriaAndAno(categoria, ano)
                .map(meta -> {
                    final BigDecimal totalAportes = aporteService
                            .calcularTotalAportes(ano);
                    meta.setValorMeta(totalAportes);
                    repository.save(meta);
                    log.info(MSG_VALOR_META_ATUALIZADO, categoria, ano, totalAportes);
                    return true;
                })
                .orElse(false);
    }
}
