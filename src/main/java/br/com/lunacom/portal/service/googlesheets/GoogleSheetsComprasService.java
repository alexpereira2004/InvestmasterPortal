package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.ComprasRowConverter;
import br.com.lunacom.portal.converter.googlesheets.GoogleSheetsRowConverter;
import br.com.lunacom.portal.domain.dto.googlesheets.CarteiraDto;
import br.com.lunacom.portal.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public abstract class GoogleSheetsComprasService
        implements GoogleSheetsDataServiceInterface<CarteiraDto> {

    public static final String MSG_ERRO = "A leitura não vai resultar nenhum item novo pois algum dos marcadores não foi encontrado (\"{}\" ou \"{}\")";
    protected abstract String getMarcadorDeInicio();
    protected abstract String getMarcadorDeFinal();

    private final ComprasRowConverter converter;

    @Override
    public GoogleSheetsRowConverter<CarteiraDto> getConverter() {
        return converter;
    }

    @Override
    public List<CarteiraDto> lerPlanilha(LeituraPlanilhaRequestDto dto) throws IOException {
        final ValueRange valueRange = this.obterDados(dto);
        final List<CarteiraDto> compras = convertAll(valueRange.getValues());

        List<CarteiraDto> comprasEfetivasList = filtrarPorMarcadores(compras);
        comprasEfetivasList = comprasEfetivasList.stream()
                .filter(e -> !e.getQuantidade().equals(0))
                .collect(Collectors.toList());
        
        return comprasEfetivasList;
    }

    private List<CarteiraDto> filtrarPorMarcadores(List<CarteiraDto> lista) {
        int inicio = -1;
        int fim = lista.size();

        for (int i = 0; i < lista.size(); i++) {
            String header = lista.get(i).getHeader();
            if (getMarcadorDeInicio().equals(header)) {
                inicio = i + 2;
            }
            String codigo = lista.get(i).getCodigoAtivo();
            if (getMarcadorDeFinal().equals(codigo) && inicio != -1) {
                fim = i;
                break;
            }
        }

        if (inicio != -1 && inicio < fim) {
            return lista.subList(inicio, fim);
        }

        log.error(MSG_ERRO, getMarcadorDeInicio(), getMarcadorDeFinal());
        return Collections.emptyList();
    }
}
