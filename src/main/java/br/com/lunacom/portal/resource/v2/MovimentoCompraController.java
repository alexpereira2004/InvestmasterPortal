package br.com.lunacom.portal.resource.v2;

import br.com.lunacom.portal.converter.Converter;
import br.com.lunacom.portal.domain.MovimentoCompra;
import br.com.lunacom.portal.domain.request.MovimentoCompraRequest;
import br.com.lunacom.portal.domain.response.MovimentoCompraResponse;
import br.com.lunacom.portal.repository.GenericRepository;
import br.com.lunacom.portal.resource.v2.specification.MovimentoCompraSpecification;
import br.com.lunacom.portal.util.DataUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/movimento-compra")
public class MovimentoCompraController extends
        GenericController<MovimentoCompra,
                          MovimentoCompraRequest,
                          MovimentoCompraResponse,
                          MovimentoCompraSpecification> {

    public MovimentoCompraController(
            GenericRepository<MovimentoCompra> repository,
            DataUtil dataUtil,
            Converter<MovimentoCompraRequest, MovimentoCompra> requestConverter,
            Converter<MovimentoCompraResponse, MovimentoCompra> responseConverter,
            MovimentoCompraSpecification specification) {
        super(repository, dataUtil, requestConverter, responseConverter, specification);
    }
}
