package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.CarteiraRowConverter;
import br.com.lunacom.portal.converter.googlesheets.GoogleSheetsRowConverter;
import br.com.lunacom.portal.domain.dto.googlesheets.CarteiraDto;
import br.com.lunacom.portal.service.CarteiraService;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("googlesheets-carteira")
public class GoogleSheetsCarteiraService
        implements GoogleSheetsDataServiceInterface<CarteiraDto> {

    private final CarteiraRowConverter converter;
    private final CarteiraService carteiraService;
    private static String INICIO = "Total Movimentações Ações";
    private static String FINAL = "Total Consolidado Ações";

    @Override
    public GoogleSheetsRowConverter<CarteiraDto> getConverter() {
        return converter;
    }

    @Override
    public List<CarteiraDto> lerPlanilha(String spreadsheetId, String range) throws IOException {
        final ValueRange valueRange = this.obterDados(spreadsheetId, range);

        final List<CarteiraDto> carteiraDtos = convertAll(valueRange.getValues());

        List<CarteiraDto> carteiraDtoLimpa = filtrarPorMarcadores(carteiraDtos);
        carteiraDtoLimpa = carteiraDtoLimpa.stream()
                .filter(e -> !e.getQuantidade().equals(0))
                .collect(Collectors.toList());

        carteiraService.salvarLista(carteiraDtoLimpa);

        return carteiraDtoLimpa;
    }

    private List<CarteiraDto> filtrarPorMarcadores(List<CarteiraDto> lista) {
        int inicio = -1;
        int fim = lista.size();

        for (int i = 0; i < lista.size(); i++) {
            String codigo = lista.get(i).getCodigoAtivo();
            if (INICIO.equals(codigo)) {
                inicio = i + 1;
            }
            if (FINAL.equals(codigo) && inicio != -1) {
                fim = i;
                break;
            }
        }

        if (inicio != -1 && inicio < fim) {
            return lista.subList(inicio, fim);
        }

        return Collections.emptyList();
    }
}
