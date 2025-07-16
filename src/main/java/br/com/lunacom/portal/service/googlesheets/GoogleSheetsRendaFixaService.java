package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.GoogleSheetsRowConverter;
import br.com.lunacom.portal.converter.googlesheets.RendaFixaRowConverter;
import br.com.lunacom.portal.domain.AgendamentoConfig;
import br.com.lunacom.portal.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import br.com.lunacom.portal.domain.dto.googlesheets.RendaFixaDto;
import br.com.lunacom.portal.domain.enumeration.Status;
import br.com.lunacom.portal.repository.AgendamentoConfigRepository;
import br.com.lunacom.portal.service.RendaFixaService;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service("googlesheets-renda-fixa")
public class GoogleSheetsRendaFixaService implements GoogleSheetsDataServiceInterface<RendaFixaDto> {

    private final RendaFixaRowConverter converter;
    private final RendaFixaService service;
    private final AgendamentoConfigRepository agendamentoConfigRepository;
    @Value("${app.googleSheet.spreadsheetId}")
    private String spreadsheetId;
    @Value("${app.googleSheet.range.renda-fixa}")
    private String range;


    @Override
    public GoogleSheetsRowConverter<RendaFixaDto> getConverter() {
        return converter;
    }

    @Override
    public List<RendaFixaDto> lerPlanilha(LeituraPlanilhaRequestDto dto) throws IOException {
        final ValueRange valueRange = this.obterDados(dto);
        final List<RendaFixaDto> rowList = convertAll(valueRange.getValues());

        ajustesColunasMescladas(rowList);

        if (Boolean.TRUE.equals(dto.getSave())) {
            Set<String> instituicoesDistintas = separarInstituicoesUnicas(rowList);

            if (service.dadosRendaFixaDoAnoNaoExistem(dto.getAno())) {
                service.montarTabelaAno(dto.getAno(), instituicoesDistintas);
            }
            service.compararDadosAtuaisESalvar(dto, rowList);
        }

        return rowList;
    }

    private Set<String> separarInstituicoesUnicas(List<RendaFixaDto> rowList) {
        return rowList.stream()
                .map(RendaFixaDto::getInstituicao)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private void ajustesColunasMescladas(List<RendaFixaDto> rowList) {
        int i = 0;
        while (i < rowList.size()) {
            RendaFixaDto rendaFixa = rowList.get(i);
            if (rendaFixa.getData().isEmpty()) {
                rowList.get(i).setData(rowList.get(i-1).getData());
                rowList.get(i).setCdiMes(rowList.get(i-1).getCdiMes());
            }
            i++;
        }
    }

    public Runnable criarTask(AgendamentoConfig config) {
        return () -> {
            log.info(MSG_JOB, config.getNome(), LocalTime.now());
            final Status status = agendamentoConfigRepository
                    .findByNome(config.getNome())
                    .get().getStatus();
            if (status.equals(Status.ATIVO)) {
                LeituraPlanilhaRequestDto dto = LeituraPlanilhaRequestDto.builder()
                        .spreadsheetId(this.spreadsheetId)
                        .range(this.range)
                        .save(true)
                        .ano("2025")
                        .build();

                try {
                    this.lerPlanilha(dto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                log.info(MSG_JOB_INATIVO, config.getNome());
            }

        };
    }
}
