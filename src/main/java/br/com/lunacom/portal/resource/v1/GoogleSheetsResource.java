package br.com.lunacom.portal.resource.v1;

import br.com.lunacom.portal.domain.dto.googlesheets.CotacaoDto;
import br.com.lunacom.portal.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import br.com.lunacom.portal.service.googlesheets.GoogleSheetsDataServiceInterface;
import br.com.lunacom.portal.service.googlesheets.ServiceFactory;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/v1/google-sheets")
public class GoogleSheetsResource {

    private final ServiceFactory factory;

    @Operation(
            summary = "Endpoint usado para fazer as leituras de integração com Planilhas Google",
            description = "A PathVariable \"Tipo\" vai ser necessária para definir qual o tipo de integração está sendo feita." +
                    "É necessário informar o ID da planilha a ser lida."
    )
    @GetMapping("/{tipo}")
    public List<?> lerAtivosCarteira(@ModelAttribute LeituraPlanilhaRequestDto dto,
                                     @PathVariable String tipo) throws IOException {

        GoogleSheetsDataServiceInterface<?> service =  factory.getService(tipo);
        return service.lerPlanilha(dto);

    }

    @GetMapping("/read-sheet")
    public List<CotacaoDto> readSheet(
            @RequestParam LeituraPlanilhaRequestDto dto) throws IOException {

        final GoogleSheetsDataServiceInterface<CotacaoDto>
                cotacaoService = factory.getService("cotacaoService");
        return cotacaoService.lerPlanilha(dto);
    }

}