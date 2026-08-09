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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Year;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static br.com.lunacom.portal.util.MonitorConstants.META_NAO_ENCONTRADA;

@Slf4j
@RequiredArgsConstructor
@Service
public class MetaService {
    public static final String MSG_VALOR_META_ATUALIZADO = "O valor da meta {} para o ano {} foi atualizada para {}.";
    public static final String NENHUM_APORTE_ENCONTRADO_PARA_O_ANO = "Nenhum aporte encontrado para o ano {}.";
    public static final String TOTAL_DE_APORTES_PARA_O_ANO = "Total de aportes para o ano {}: {}";
    public static final String RENDA_FIXA = "Renda Fixa";
    public static final String O_ANO_NAO_PODE_SER_NULO = "O ano não pode ser nulo";
    private final MetaRepository repository;
    private final AporteRepository aporteRepository;
    private final AporteService aporteService;
    private record PeriodoAnual(LocalDate primeiroDia, LocalDate ultimoDia) {}

    public Optional<Meta> pesquisarPorId(Integer id) {
        return repository.findById(id);
    }

    public Meta pesquisarUmaMetaPorCategoriaEAno(
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
                            .calcularTotalAportesProprios(ano);
                    meta.setValorMeta(totalAportes);
                    repository.save(meta);
                    log.info(MSG_VALOR_META_ATUALIZADO, categoria, ano, totalAportes);
                    return true;
                })
                .orElse(false);
    }


    public DetalheInvestimentoAnualResponse pesquisarDetalhesInvestimentoAnualBruto(Integer ano) {
        DetalheInvestimentoAnualResponse response = new DetalheInvestimentoAnualResponse();
        PeriodoAnual periodo = obterPeriodoPorAno(ano);

        final List<Aporte> listaApostes = buscarAportes(ano, periodo);

        definirAportesRealizados(listaApostes, response);

        definirTotalRendaFixa(response);

        final BigDecimal totalAporteProprio = definirTotalAporteProprio(response);
        
        definirProjecaoInicial(ano, response);

        definirProjecaoFutura(response, totalAporteProprio);
        return response;

    }


    private List<Aporte> buscarAportes(Integer ano, PeriodoAnual periodo) {
        final List<Aporte> resultado = aporteRepository.findByDataAporteBetweenOrderByDataAporteDesc(
                periodo.primeiroDia(), periodo.ultimoDia());
        if (resultado.isEmpty()) {
            log.info(NENHUM_APORTE_ENCONTRADO_PARA_O_ANO, ano);
        } else {
            final BigDecimal totalAportes = aporteService.calcularTotalAportes(ano);
            log.info(TOTAL_DE_APORTES_PARA_O_ANO, ano, totalAportes);
        }
        return resultado;
    }

    private void definirAportesRealizados(List<Aporte> resultado, DetalheInvestimentoAnualResponse response) {
        resultado.stream()
                .filter(a -> a.getOrigem() != null &&
                        (a.getOrigem().startsWith("CC") || a.getOrigem().startsWith("Ajuste")))
                .forEach(a -> response.getAporteProprioMensalMap().merge(
                        a.getDataAporte().getMonthValue(),
                        a.getValor(),
                        BigDecimal::add
                ));

        resultado.stream()
                .filter(a -> a.getOrigem() != null && a.getOrigem().startsWith(RENDA_FIXA))
                .forEach(a -> response.getRendaFixaMensalMap().merge(
                        a.getDataAporte().getMonthValue(),
                        a.getValor(),
                        BigDecimal::add
                ));
        preencherComZeros(response);
    }


    private void preencherComZeros(DetalheInvestimentoAnualResponse response) {
        for (int mes = 1; mes <= 12; mes++) {
            if (!response.getAporteProprioMensalMap().containsKey(mes)) {
                response.getAporteProprioMensalMap().put(mes, BigDecimal.ZERO);
            }
            if (!response.getRendaFixaMensalMap().containsKey(mes)) {
                response.getRendaFixaMensalMap().put(mes, BigDecimal.ZERO);
            }
        }
    }

    private PeriodoAnual obterPeriodoPorAno(Integer ano) {
        if (ano == null) {
            throw new IllegalArgumentException(O_ANO_NAO_PODE_SER_NULO);
        }

        Year anoObjeto = Year.of(ano);
        LocalDate primeiroDia = anoObjeto.atDay(1);
        LocalDate ultimoDia = anoObjeto.atDay(anoObjeto.length());

        return new PeriodoAnual(primeiroDia, ultimoDia);
    }

    private BigDecimal definirTotalRendaFixa(DetalheInvestimentoAnualResponse response) {
        final BigDecimal totalRendaFixa = response.getRendaFixaMensalMap().values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        response.setTotalRendaFixa(totalRendaFixa);
        return totalRendaFixa;
    }

    private BigDecimal definirTotalAporteProprio(DetalheInvestimentoAnualResponse response) {
        final BigDecimal totalAporteProprio = response.getAporteProprioMensalMap().values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        response.setTotalAporteProprio(totalAporteProprio);
        return totalAporteProprio;
    }

    private void definirProjecaoInicial(Integer ano, DetalheInvestimentoAnualResponse response) {
        final List<Meta> metas = this.pesquisarMetaAporteMensal(ano);
        metas.sort(Comparator.comparing(Meta::getCategoria));
        for (int i = 0; i < metas.size(); i++) {
            int mes = i + 1;
            BigDecimal valorMeta = metas.get(i).getValorMeta();
            response.getProjecaoInicialAportes().put(mes, valorMeta);
        }
    }
    
    private void definirProjecaoFutura(DetalheInvestimentoAnualResponse response, BigDecimal totalAporteProprio) {
        final int mesAtual = LocalDate.now().getMonthValue();

        final int mesesFechados = Math.max(mesAtual - 1, 1);

        final BigDecimal totalAportesCompletos = totalAporteProprio.subtract(
                response.getAporteProprioMensalMap().getOrDefault(mesAtual, BigDecimal.ZERO)
        );

        final BigDecimal projecaoMensal = totalAportesCompletos
                .divide(BigDecimal.valueOf(mesesFechados), 2, RoundingMode.HALF_UP);

        for (int mes = 1; mes <= 12; mes++) {
            if (mes < mesAtual) {
                response.getProjecaoFuturaAportes().put(mes, BigDecimal.ZERO);
            } else {
                response.getProjecaoFuturaAportes().put(mes, projecaoMensal);
            }
        }
    }


    private List<Meta> pesquisarMetaAporteMensal(Integer ano) {

        final List<Meta> listaMetas = repository
                .buscarPorAnoECategoriaLike(ano, "META_APORTE_MENSAL_");
        if(listaMetas.isEmpty()) {
            log.info(META_NAO_ENCONTRADA);
            log.info(ano.toString());
        }
        return listaMetas;
    }
}
