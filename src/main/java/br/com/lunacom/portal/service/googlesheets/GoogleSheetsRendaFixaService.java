package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.GoogleSheetsRowConverter;
import br.com.lunacom.portal.converter.googlesheets.RendaFixaRowConverter;
import br.com.lunacom.portal.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import br.com.lunacom.portal.domain.dto.googlesheets.RendaFixaDto;
import br.com.lunacom.portal.service.ProdutoFinanceiroService;
import br.com.lunacom.portal.service.RendaFixaService;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private final ProdutoFinanceiroService produtoFinanceiroService;

    @Override
    public GoogleSheetsRowConverter<RendaFixaDto> getConverter() {
        return converter;
    }

    @Override
    public List<RendaFixaDto> lerPlanilha(LeituraPlanilhaRequestDto dto) throws IOException {
        final ValueRange valueRange = this.obterDados(dto);
        final List<RendaFixaDto> rowList = convertAll(valueRange.getValues());

        ajustesColunasMescladas(rowList);

        if (dto.getSave()) {
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
}
