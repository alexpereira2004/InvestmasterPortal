package br.com.lunacom.portal.service.monitor;

import br.com.lunacom.comum.domain.dto.CotacaoAgoraDto;
import br.com.lunacom.comum.domain.entity.monitor.RegraCompraPorHistoricoVenda;
import br.com.lunacom.comum.domain.enumeration.Status;
import br.com.lunacom.comum.domain.enumeration.TipoMovimento;
import br.com.lunacom.portal.converter.monitor.RegraCompraPorHistoricoVendaResponseConverter;
import br.com.lunacom.portal.domain.request.monitor.RegraCompraPorHistoricoVendaConsultaRequest;
import br.com.lunacom.portal.domain.response.RegraCompraPorHistoricoVendaResponse;
import br.com.lunacom.portal.repository.monitor.RegraCompraPorHistoricoVendaRepository;
import br.com.lunacom.portal.resource.v1.specification.RegraCompraPorHistoricoVendaSpecification;
import br.com.lunacom.portal.service.CotacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegraCompraPorHistoricoVendaService {
    private final RegraCompraPorHistoricoVendaRepository repository;
    private final CotacaoService cotacaoService;
    private final RegraCompraPorHistoricoVendaResponseConverter responseConverter;

    public RegraCompraPorHistoricoVenda salvar(RegraCompraPorHistoricoVenda entity) {
        repository.inativarRegrasPorMonitor(entity.getMonitor().getId());
        entity.setStatus(Status.ATIVO);
        entity.setTipo(TipoMovimento.COMPRA);
        return repository.save(entity);
    }

    public Page<RegraCompraPorHistoricoVendaResponse> pesquisarComPaginacao(
            RegraCompraPorHistoricoVendaConsultaRequest request,
            Pageable pageable) {
        final List<CotacaoAgoraDto> cotacaoAgoraDtos = cotacaoService
                .pesquisarCotacaoAgora();
        final RegraCompraPorHistoricoVendaSpecification specification = RegraCompraPorHistoricoVendaSpecification
                .builder()
                .request(request)
                .build();
        final Page<RegraCompraPorHistoricoVenda> all = repository
                .findAll(specification, pageable);
        final Page<RegraCompraPorHistoricoVendaResponse> response = all.map(e -> responseConverter.encode(e));
        response.forEach(regra -> {
            final BigDecimal cotacaoAtual = cotacaoAgoraDtos.stream()
                    .filter(i -> i.getCodigo().equals(regra.getNome()))
                    .findFirst()
                    .orElse(new CotacaoAgoraDto())
                    .getCotacaoAtual();
            regra.setCotacaoAtual(String.valueOf(cotacaoAtual));
        });

        return response;
    }
}
