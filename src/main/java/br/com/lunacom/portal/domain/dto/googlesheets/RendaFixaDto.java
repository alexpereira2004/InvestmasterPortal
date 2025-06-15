package br.com.lunacom.portal.domain.dto.googlesheets;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RendaFixaDto {
    private String header;
    private String data;
    private String instituicao;
    private Double renda;
    private Double investido;
    private Double rentabilidade;
    private Double comparaticoComCdi;
    private Double cdiMes;
}
