package br.com.lunacom.portal.resource.v1;

import br.com.lunacom.portal.domain.dto.googlesheets.CotacaoDto;
import br.com.lunacom.portal.service.googlesheets.GoogleSheetsDataServiceInterface;
import br.com.lunacom.portal.service.googlesheets.ServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/v1/google-sheets")
public class GoogleSheetsResource {

    private final ServiceFactory factory;

    @GetMapping("/read-sheet")
    public List<CotacaoDto> readSheet(
            @RequestParam String spreadsheetId,
            @RequestParam String range) throws IOException {

        final GoogleSheetsDataServiceInterface<CotacaoDto>
                cotacaoService = factory.getService("cotacaoService");
        return cotacaoService.lerPlanilha(spreadsheetId, range);
    }

    @GetMapping("/{tipo}")
    public List<?> lerAtivosCarteira(@RequestParam String spreadsheetId,
                                     @RequestParam String range,
                                     @RequestParam Boolean save,
                                     @PathVariable String tipo) throws IOException {

        GoogleSheetsDataServiceInterface<?> service =  factory.getService(tipo);
        return service.lerPlanilha(spreadsheetId, range);
    }
}