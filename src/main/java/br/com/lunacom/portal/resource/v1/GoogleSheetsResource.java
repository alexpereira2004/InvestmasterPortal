package br.com.lunacom.portal.resource.v1;

import br.com.lunacom.portal.domain.dto.GoogleSpreadsheetCotacaoDto;
import br.com.lunacom.portal.service.GoogleSheetsDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/v1/google")
public class GoogleSheetsResource {

    private final GoogleSheetsDataService googleSheetsDataService;

    @GetMapping("/read-sheet")
    public List<GoogleSpreadsheetCotacaoDto> readSheet(
            @RequestParam String spreadsheetId,
            @RequestParam String range) throws IOException {
        return googleSheetsDataService.lerPlanilha(spreadsheetId, range);
    }
}