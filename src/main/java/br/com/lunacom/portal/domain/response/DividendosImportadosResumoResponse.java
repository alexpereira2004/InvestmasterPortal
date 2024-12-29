package br.com.lunacom.portal.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DividendosImportadosResumoResponse extends DividendoResponse {
    private Long totalDividendosImportados;

    DividendosImportadosResumoResponse(Integer id, LocalDate dataRecebimento, String tipo, Integer quantidade, Double dividendo, Double valorTotal, String ativoNome, String ativoCodigo, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
        super(id, dataRecebimento, tipo, quantidade, dividendo, valorTotal, ativoNome, ativoCodigo, dataCriacao, dataAtualizacao);
    }
}
