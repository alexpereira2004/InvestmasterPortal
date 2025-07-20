package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.CarteiraRowConverter;
import br.com.lunacom.portal.domain.enumeration.AcaoTipo;
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
    protected AcaoTipo getTipo() {
        return AcaoTipo.BDR;
    }

    @Override
    protected String getInicio() {
        return INICIO;
    }

    @Override
    protected String getFinal() {
        return FINAL;
    }

    @Override
    public String getSpreadsheetId() {
        return null;
    }

    @Override
    public String getRange() {
        return null;
    }
}
