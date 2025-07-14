package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.CarteiraRowConverter;
import br.com.lunacom.portal.domain.enumeration.AcaoTipo;
import br.com.lunacom.portal.service.CarteiraService;
import org.springframework.stereotype.Service;

@Service("googlesheets-carteira-fiis")
public class GoogleSheetsCarteiraFiisService extends GoogleSheetsCarteiraService {

    private static String INICIO = "Total Movimentações FIIs";
    private static String FINAL = "Total Consolidado FIIs";

    public GoogleSheetsCarteiraFiisService(CarteiraRowConverter converter, CarteiraService carteiraService) {
        super(converter, carteiraService);
    }

    @Override
    protected AcaoTipo getTipo() {
        return AcaoTipo.FII;
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
    public Runnable criarTask(String name) {
        return null;
    }
}
