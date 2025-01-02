package br.com.lunacom.portal.resource.v1;

import br.com.lunacom.portal.service.GoogleSheetsDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value="/v1/google")
public class GoogleSheetsController {

    @Autowired
    private GoogleSheetsDataService googleSheetsDataService;

    @GetMapping("/read-sheet")
    public List<List<Object>> readSheet(
            @RequestParam String spreadsheetId,
            @RequestParam String range) throws IOException {
        return googleSheetsDataService.readSpreadsheet(spreadsheetId, range);
    }
}