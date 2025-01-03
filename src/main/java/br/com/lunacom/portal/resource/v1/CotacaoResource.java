package br.com.lunacom.portal.resource.v1;

import br.com.lunacom.portal.service.CotacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
}
