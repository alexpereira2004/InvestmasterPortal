package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.ComprasRowConverter;
import org.springframework.stereotype.Service;

@Service("googlesheets-compras-acoes")
public class GoogleSheetsComprasAcoesService extends GoogleSheetsComprasService {

    private static String INICIO = "Ações BR";
    private static String FINAL = "Total Movimentações Ações";

    public GoogleSheetsComprasAcoesService(ComprasRowConverter converter) {
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
