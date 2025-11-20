package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.comum.domain.enumeration.AcaoTipo;
import br.com.lunacom.portal.converter.googlesheets.CarteiraRowConverter;
import br.com.lunacom.portal.converter.googlesheets.GoogleSheetsRowConverter;
import br.com.lunacom.portal.domain.Carteira;
import br.com.lunacom.portal.domain.dto.googlesheets.CarteiraDto;
import br.com.lunacom.portal.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import br.com.lunacom.portal.service.CarteiraService;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public abstract class GoogleSheetsCarteiraService
        implements GoogleSheetsDataServiceInterface<CarteiraDto> {

    public static final String MSG_ERRO = "A leitura não vai resultar nenhum item novo pois algum dos marcadores não foi encontrado (\"{}\" ou \"{}\")";
    private final CarteiraRowConverter converter;
    private final CarteiraService carteiraService;

    protected abstract AcaoTipo getTipo();
    protected abstract String getInicio();
    protected abstract String getFinal();

    @Override
    public GoogleSheetsRowConverter<CarteiraDto> getConverter() {
        return converter;
    }

    @Override
    public List<CarteiraDto> lerPlanilha(LeituraPlanilhaRequestDto dto) throws IOException {
        final ValueRange valueRange = this.obterDados(dto);

        final List<CarteiraDto> carteiraDtos = convertAll(valueRange.getValues());

        List<CarteiraDto> carteiraDtoLimpa = filtrarPorMarcadores(carteiraDtos);
        carteiraDtoLimpa = carteiraDtoLimpa.stream()
                .filter(e -> !e.getQuantidade().equals(0))
                .collect(Collectors.toList());

        if (dto.getSave()) {
            carteiraService.salvarLista(carteiraDtoLimpa);
            carteiraService.removerPorCodigoAtivo(identificarAtivosDescontinuados(carteiraDtoLimpa));
        }

        return carteiraDtoLimpa;
    }

    private List<String> identificarAtivosDescontinuados(List<CarteiraDto> listaA) {

        Set<String> conjuntoA = listaA.stream()
                .map(i -> i.getCodigoAtivo()).collect(Collectors.toSet());

        final List<Carteira> carteiraList = carteiraService.pesquisar();

        Set<String> conjuntoB = carteiraList.stream()
                .filter(c -> c.getAtivo().getTipo().equals(getTipo().getCodigo()))
                .map(c -> c.getAtivo().getCodigo())
                .collect(Collectors.toSet());

        final List<String> diferenca = conjuntoB.stream()
                .filter(a -> !conjuntoA.contains(a))
                .collect(Collectors.toList());

        return diferenca;
    }

    private List<CarteiraDto> filtrarPorMarcadores(List<CarteiraDto> lista) {
        int inicio = -1;
        int fim = lista.size();

        for (int i = 0; i < lista.size(); i++) {
            String codigo = lista.get(i).getCodigoAtivo();
            if (getInicio().equals(codigo)) {
                inicio = i + 1;
            }
            if (getFinal().equals(codigo) && inicio != -1) {
                fim = i;
                break;
            }
        }

        if (inicio != -1 && inicio < fim) {
            return lista.subList(inicio, fim);
        }

        log.error(MSG_ERRO, getInicio(), getFinal());
        return Collections.emptyList();
    }
}
