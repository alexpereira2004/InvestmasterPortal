package br.com.lunacom.portal.service;

import br.com.lunacom.comum.domain.Aporte;
import br.com.lunacom.comum.domain.entity.meta.Meta;
import br.com.lunacom.portal.domain.response.DetalheInvestimentoAnualResponse;
import br.com.lunacom.portal.repository.AporteRepository;
import br.com.lunacom.portal.repository.MetaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import static br.com.lunacom.portal.util.MonitorConstants.META_NAO_ENCONTRADA;

@Slf4j
@RequiredArgsConstructor
@Service
public class MetaService {
    public static final String MSG_VALOR_META_ATUALIZADO = "O valor da meta {} para o ano {} foi atualizada para {}.";
    private final MetaRepository repository;
    private final AporteRepository aporteRepository;
    private final AporteService aporteService;
    private record PeriodoAnual(LocalDate primeiroDia, LocalDate ultimoDia) {}

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


    public DetalheInvestimentoAnualResponse pesquisarDetalhesInvestimentoAnualBruto(Integer ano) {
        PeriodoAnual periodo = obterPeriodoPorAno(ano);
        final List<Aporte> resultado = aporteRepository.findByDataAporteBetweenOrderByDataAporteDesc(
                periodo.primeiroDia(), periodo.ultimoDia());
        if (resultado.isEmpty()) {
            log.info("Nenhum aporte encontrado para o ano {}.", ano);
        } else {
            final BigDecimal totalAportes = aporteService.calcularTotalAportes(ano);
            log.info("Total de aportes para o ano {}: {}", ano, totalAportes);
        }

        DetalheInvestimentoAnualResponse response = new DetalheInvestimentoAnualResponse();

        resultado.stream()
                .filter(a -> a.getOrigem() != null &&
                        (a.getOrigem().startsWith("CC") || a.getOrigem().startsWith("Ajuste")))
                .forEach(a -> response.getAporteProprioMensalMap().merge(
                        a.getDataAporte().getMonthValue(),
                        a.getValor(),
                        BigDecimal::add
                ));

        resultado.stream()
                .filter(a -> a.getOrigem() != null && a.getOrigem().startsWith("Renda Fixa"))
                .forEach(a -> response.getRendaFixaMensalMap().merge(
                        a.getDataAporte().getMonthValue(),
                        a.getValor(),
                        BigDecimal::add
                ));

        for (int mes = 1; mes <= 12; mes++) {
            if (!response.getAporteProprioMensalMap().containsKey(mes)) {
                response.getAporteProprioMensalMap().put(mes, BigDecimal.ZERO);
            }
            if (!response.getRendaFixaMensalMap().containsKey(mes)) {
                response.getRendaFixaMensalMap().put(mes, BigDecimal.ZERO);
            }
        }

        return response;

    }

    private PeriodoAnual obterPeriodoPorAno(Integer ano) {
        if (ano == null) {
            throw new IllegalArgumentException("O ano não pode ser nulo.");
        }

        Year anoObjeto = Year.of(ano);
        LocalDate primeiroDia = anoObjeto.atDay(1);
        LocalDate ultimoDia = anoObjeto.atDay(anoObjeto.length());

        return new PeriodoAnual(primeiroDia, ultimoDia);
    }
}
