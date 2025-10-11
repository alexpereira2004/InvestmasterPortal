package br.com.lunacom.portal.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CotacaoHistoricoDto {
    private String id;
    private String nomeAtivo;
    private String codigo;
    private BigDecimal primeiroPreco;
    private BigDecimal ultimoPreco;
    private BigDecimal variacao;
    private BigDecimal precoMedio;
    private BigDecimal relacaoUltimoPrecoPrecoMedio;
    private LocalDate referenciaInicio;
    private LocalDate referenciaFinal;
    private Integer quantidadeLeituras;
    private BigDecimal minimaPeriodo;
    private BigDecimal maximaPeriodo;
    private LocalDateTime importacao;
}
