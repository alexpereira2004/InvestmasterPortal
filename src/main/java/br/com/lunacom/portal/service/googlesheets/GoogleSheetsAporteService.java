package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.AporteRowConverter;
import br.com.lunacom.portal.converter.googlesheets.GoogleSheetsRowConverter;
import br.com.lunacom.portal.domain.Aporte;
import br.com.lunacom.portal.domain.dto.googlesheets.AporteDto;
import br.com.lunacom.portal.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import br.com.lunacom.portal.service.AporteService;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
@RequiredArgsConstructor
@Service("googlesheets-aporte")
public class GoogleSheetsAporteService implements GoogleSheetsDataServiceInterface<AporteDto> {

    private final AporteRowConverter converter;
    private final AporteService service;

    @Override
    public GoogleSheetsRowConverter<AporteDto> getConverter() {
        return converter;
    }

    @Override
    public Runnable criarTask(String name) {
        return null;
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
