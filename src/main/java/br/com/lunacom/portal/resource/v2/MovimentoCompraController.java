package br.com.lunacom.portal.resource.v2;

import br.com.lunacom.portal.converter.Converter;
import br.com.lunacom.portal.domain.MovimentoCompra;
import br.com.lunacom.portal.domain.request.MovimentoCompraRequest;
import br.com.lunacom.portal.repository.MovimentoCompraRepository;
import br.com.lunacom.portal.util.DataUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/movimento-compra")
public class MovimentoCompraController extends GenericController<MovimentoCompra, MovimentoCompraRequest>{

    public MovimentoCompraController(MovimentoCompraRepository repo,
                                     DataUtil dataUtil,
                                     Converter<MovimentoCompraRequest, MovimentoCompra> requestConverter) {
        super(repo, dataUtil, requestConverter);
    }
}
