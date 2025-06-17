package br.com.lunacom.portal.domain.dto.googlesheets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LeituraPlanilhaRequestDto {
    private String spreadsheetId;
    private String range;
    private Boolean save;
    private String ano;
}
