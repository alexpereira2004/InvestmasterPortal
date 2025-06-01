package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.ComprasRowConverter;
import org.springframework.stereotype.Service;

@Service("googlesheets-compras-bdrs")
public class GoogleSheetsComprasBdrsService extends GoogleSheetsComprasService {

    private static String INICIO = "BDRs";
    private static String FINAL = "Total Movimentações BDRs";

    public GoogleSheetsComprasBdrsService(ComprasRowConverter converter) {
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
