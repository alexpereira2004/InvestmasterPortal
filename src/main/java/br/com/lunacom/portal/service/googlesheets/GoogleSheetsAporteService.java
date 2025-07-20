package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.AporteRowConverter;
import br.com.lunacom.portal.converter.googlesheets.GoogleSheetsRowConverter;
import br.com.lunacom.portal.domain.AgendamentoConfig;
import br.com.lunacom.portal.domain.Aporte;
import br.com.lunacom.portal.domain.dto.googlesheets.AporteDto;
import br.com.lunacom.portal.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import br.com.lunacom.portal.repository.AgendamentoConfigRepository;
import br.com.lunacom.portal.service.AporteService;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static br.com.lunacom.portal.domain.enumeration.FormatosData.DD_MM_YYYY_BARRA;
import static br.com.lunacom.portal.util.StringParser.toLocalDate;

@Slf4j
@Service("googlesheets-aporte")
public class GoogleSheetsAporteService extends TarefaBase
        implements GoogleSheetsDataServiceInterface<AporteDto> {

    private final AporteRowConverter converter;
    private final AporteService service;
    @Value("${app.googleSheet.spreadsheetId}")
    private String spreadsheetId;
    @Value("${app.googleSheet.range.aportes}")
    private String range;

    public GoogleSheetsAporteService(AgendamentoConfigRepository agendamentoConfigRepository,
                                     AporteRowConverter converter,
                                     AporteService service) {
        super(agendamentoConfigRepository);
        this.converter = converter;
        this.service = service;
    }

    @PostConstruct
    public void init() {
        this.setSpreadsheetId(spreadsheetId);
        this.setRange(range);
    }

    @Override
    public GoogleSheetsRowConverter<AporteDto> getConverter() {
        return converter;
    }

    @Override
    public Runnable criarTask(AgendamentoConfig config) {
        return this.criarTarefaBase(config, this);
    }

    @Override
    public List<AporteDto> lerPlanilha(LeituraPlanilhaRequestDto dto) throws IOException {
        final ValueRange valueRange = this.obterDados(dto);
        final List<AporteDto> rowList = convertAll(valueRange.getValues());
        if (dto.getSave()) {

            Optional<LocalDate> dataUltimoAporte = service.pesquisarUltimaDataCompra();

            final List<Aporte> result = rowList.stream()
                    .map(i -> this.converter(i))
                    .filter(aplicarFiltroPorMaiorData(dataUltimoAporte))
                    .collect(Collectors.toList());

            service.removerUltimosAportes(dataUltimoAporte);
            result.forEach(i -> service.salvar(i));
        }
        return rowList;
    }

    private Predicate<? super Aporte> aplicarFiltroPorMaiorData(Optional<LocalDate> dataUltimoAporte) {
        return i -> {
            if (!dataUltimoAporte.isPresent()) return true;

            return dataUltimoAporte.isPresent() && Objects.nonNull(i.getDataAporte()) &&
                    (i.getDataAporte().isAfter(dataUltimoAporte.get()) || i.getDataAporte().equals(dataUltimoAporte.get()));

        };
    }

    private Aporte converter(AporteDto dtoi) {
        final Aporte entity = Aporte.builder()
                .dataAporte(toLocalDate(dtoi.getData(), DD_MM_YYYY_BARRA))
                .valor(BigDecimal.valueOf(dtoi.getValor()))
                .origem(dtoi.getOrigem())
                .destino(dtoi.getDestino())
                .dataCriacao(LocalDateTime.now())
                .build();

        return entity;

    }
}
