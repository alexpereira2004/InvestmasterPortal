package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.CarteiraRowConverter;
import br.com.lunacom.portal.service.CarteiraService;
import org.springframework.stereotype.Service;

@Service("googlesheets-carteira-acoes")
public class GoogleSheetsCarteiraAcoesService extends GoogleSheetsCarteiraService {

    private static String INICIO = "Total Movimentações Ações";
    private static String FINAL = "Total Consolidado Ações";

    public GoogleSheetsCarteiraAcoesService(CarteiraRowConverter converter, CarteiraService carteiraService) {
        super(converter, carteiraService);
    }

    @Override
    protected String getInicio() {
        return INICIO;
    }

    @Override
    protected String getFinal() {
        return FINAL;
    }
}
