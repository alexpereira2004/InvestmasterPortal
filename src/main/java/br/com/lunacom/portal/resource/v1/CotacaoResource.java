package br.com.lunacom.portal.resource.v1;

import br.com.lunacom.portal.domain.dto.CotacaoAgoraDto;
import br.com.lunacom.portal.domain.dto.CotacaoHistoricoDto;
import br.com.lunacom.portal.domain.dto.ReferenciaRangeDto;
import br.com.lunacom.portal.domain.request.ExtratoCotacaoRequest;
import br.com.lunacom.portal.domain.wrapper.ExtratoCotacaoWrapper;
import br.com.lunacom.portal.service.CotacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDate;
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

    @GetMapping("/historico")
    public List<CotacaoHistoricoDto> historico(
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
        @RequestParam(required = false) String tipoAtivo,
        @RequestParam(required = false) List<String> ativos
    ) {
        return cotacaoService.buscarHistorico(dataInicio, dataFim, tipoAtivo, ativos);
    }


    @GetMapping("/primeira-e-ultima")
    public ReferenciaRangeDto primeiraE_Ultima(@RequestParam String codigo) {
        return cotacaoService.primeiraE_Ultima(codigo);
    }

}
