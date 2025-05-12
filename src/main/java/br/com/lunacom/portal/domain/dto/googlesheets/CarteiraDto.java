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
    String header;
    String codigoAtivo;
    String estrategia;
    Double precoPago;
    Double precoAtual;
    Double valorizacao;
    Integer quantidade;
    Double totalInvestido;
    Double atualizacao;
    Double resultado;
    LocalDate dataCompra;
}
