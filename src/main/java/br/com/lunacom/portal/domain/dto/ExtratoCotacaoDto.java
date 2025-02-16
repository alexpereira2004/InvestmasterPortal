package br.com.lunacom.portal.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ExtratoCotacaoDto {
    private String codigo;
    private String nome;
    private Double preco;
    private Double variacao;
    private LocalDate referencia;
    private LocalDateTime importacao;
}
