package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.comum.domain.enumeration.AcaoTipo;
import br.com.lunacom.portal.converter.googlesheets.CarteiraRowConverter;
import br.com.lunacom.portal.service.CarteiraService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("googlesheets-carteira-bdrs")
public class GoogleSheetsCarteiraBdrsService extends GoogleSheetsCarteiraService {

    private static String INICIO = "Total Movimentações BDRs";
    private static String FINAL = "Total Consolidado BDRs";
    @Value("${app.googleSheet.spreadsheetId}")
    private String spreadsheetId;
    @Value("${app.googleSheet.range.carteira-bdrs}")
    private String range;

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
        return this.spreadsheetId;
    }

    @Override
    public String getRange() {
        return this.range;
    }
}
