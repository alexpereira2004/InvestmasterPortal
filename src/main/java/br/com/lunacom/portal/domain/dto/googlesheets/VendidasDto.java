package br.com.lunacom.portal.domain.dto.googlesheets;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendidasDto {
    private String header;
    private String codigoAtivo;
    private Double precoPago;
    private Double precoFinal;
    private Double rendimento;
    private Integer quantidade;
    private Double totalInvestido;
    private Double atualizacao;
    private Double diferenca;
    private LocalDate dataCompra;
    private LocalDate dataVenda;
    private Integer dias;
}
