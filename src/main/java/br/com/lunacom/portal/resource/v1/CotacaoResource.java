package br.com.lunacom.portal.resource.v1;

import br.com.lunacom.portal.domain.CotacaoAgoraDto;
import br.com.lunacom.portal.domain.request.ExtratoCotacaoRequest;
import br.com.lunacom.portal.domain.wrapper.ExtratoCotacaoWrapper;
import br.com.lunacom.portal.service.CotacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value="/v1/cotacao")
public class CotacaoResource {

    private final CotacaoService cotacaoService;

    @GetMapping("/importacao-google")
    public void importarDadosGoogle() throws IOException {
        cotacaoService.importarDadosGoogle();
    }

    @GetMapping("/extrato")
    public ExtratoCotacaoWrapper extrato(
            @ModelAttribute ExtratoCotacaoRequest request
    ) {
        return cotacaoService.gerarExtratoDeUmAtivo(request);
    }

    @GetMapping("/agora")
    public List<CotacaoAgoraDto> cotacaoAgora() {
        return cotacaoService.pesquisarCotacaoAgora();
    }

    @PostMapping("/importacao-lote-site-investing-com/{ativo}")
    public void importacaoLoteSiteInvestingCom
            (@RequestBody @Valid @NotNull String request,
             @PathVariable @NotNull String ativo) throws IOException {
        cotacaoService.importarLoteSiteInvestingCom(ativo, request);
    }
}
