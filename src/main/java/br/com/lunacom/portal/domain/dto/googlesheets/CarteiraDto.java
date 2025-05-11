package br.com.lunacom.portal.domain.dto.googlesheets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Data
public class CarteiraDto {
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
