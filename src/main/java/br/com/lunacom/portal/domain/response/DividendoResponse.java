package br.com.lunacom.portal.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DividendoResponse extends GenericResponse {
    private Integer id;
    private LocalDate dataRecebimento;
    private String tipo;
    private Integer quantidade;
    private Double dividendo;
    private Double valorTotal;
    private String ativoNome;
    private String ativoCodigo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}
