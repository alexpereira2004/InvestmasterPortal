package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.ComprasRowConverter;
import br.com.lunacom.portal.domain.TipoAtivoInterface;
import br.com.lunacom.portal.domain.enumeration.AcaoTipo;
import br.com.lunacom.portal.service.AtivoService;
import br.com.lunacom.portal.service.MovimentoCompraService;
import org.springframework.stereotype.Service;

@Service("googlesheets-compras-fiis")
public class GoogleSheetsComprasFiisService
        extends GoogleSheetsComprasService implements TipoAtivoInterface {

    private static String INICIO = "FII";
    private static String FINAL = "Total Movimentações FIIs";

    public GoogleSheetsComprasFiisService(
            ComprasRowConverter converter,
            MovimentoCompraService movimentoCompraService,
            AtivoService ativoService) {
        super(converter, movimentoCompraService, ativoService);
    }

    @Override
    protected String getMarcadorDeInicio() {
        return INICIO;
    }

    @Override
    protected String getMarcadorDeFinal() {
        return FINAL;
    }

    @Override
    public String getTipoAtivo() {
        return AcaoTipo.FII.getCodigo();
    }

    @Override
    public Runnable criarTask(String name) {
        return null;
    }
}
