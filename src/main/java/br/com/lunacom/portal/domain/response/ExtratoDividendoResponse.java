package br.com.lunacom.portal.domain.response;

import br.com.lunacom.comum.domain.dto.AtivoDividendoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ExtratoDividendoResponse {
    private List<AtivoDividendoDto> dividendos;
    private List<String> label;
}
