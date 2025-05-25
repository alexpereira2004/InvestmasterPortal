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
public class CarteiraDto {
    private String header;
    private String codigoAtivo;
    private String estrategia;
    private Double precoPago;
    private Double precoAtual;
    private Double valorizacao;
    private Integer quantidade;
    private Double totalInvestido;
    private Double atualizacao;
    private Double resultado;
    private LocalDate dataCompra;
}
