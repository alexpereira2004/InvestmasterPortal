package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.CarteiraRowConverter;
import br.com.lunacom.portal.service.CarteiraService;
import org.springframework.stereotype.Service;

@Service("googlesheets-carteira-bdrs")
public class GoogleSheetsCarteiraBdrsService extends GoogleSheetsCarteiraService {

    private static String INICIO = "Total Movimentações BDRs";
    private static String FINAL = "Total Consolidado BDRs";

    public GoogleSheetsCarteiraBdrsService(CarteiraRowConverter converter, CarteiraService carteiraService) {
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
