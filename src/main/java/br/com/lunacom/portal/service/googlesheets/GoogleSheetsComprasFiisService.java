package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.ComprasRowConverter;
import org.springframework.stereotype.Service;

@Service("googlesheets-compras-fiis")
public class GoogleSheetsComprasFiisService extends GoogleSheetsComprasService {

    private static String INICIO = "FII";
    private static String FINAL = "Total Movimentações FIIs";

    public GoogleSheetsComprasFiisService(ComprasRowConverter converter) {
        super(converter);
    }

    @Override
    protected String getMarcadorDeInicio() {
        return INICIO;
    }

    @Override
    protected String getMarcadorDeFinal() {
        return FINAL;
    }
}
