package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.ComprasRowConverter;
import br.com.lunacom.portal.domain.AgendamentoConfig;
import br.com.lunacom.portal.domain.TipoAtivoInterface;
import br.com.lunacom.portal.domain.enumeration.AcaoTipo;
import br.com.lunacom.portal.service.AtivoService;
import br.com.lunacom.portal.service.MovimentoCompraService;
import org.springframework.stereotype.Service;

@Service("googlesheets-compras-bdrs")
public class GoogleSheetsComprasBdrsService
        extends GoogleSheetsComprasService implements TipoAtivoInterface {

    private static String INICIO = "BDRs";
    private static String FINAL = "Total Movimentações BDRs";

    public GoogleSheetsComprasBdrsService(ComprasRowConverter converter,
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
        return AcaoTipo.BDR.getCodigo();
    }

    @Override
    public Runnable criarTask(AgendamentoConfig config) {
        return null;
    }
}
