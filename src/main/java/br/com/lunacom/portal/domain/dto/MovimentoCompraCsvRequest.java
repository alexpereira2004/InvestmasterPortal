package br.com.lunacom.portal.domain.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class MovimentoCompraCsvRequest {
    @CsvBindByPosition(position = 0)
    private String ativoCodigo;

    @CsvBindByPosition(position = 1)
    private String estrategia;

    @CsvBindByPosition(position = 2)
    private String precoPago;

    @CsvBindByPosition(position = 3)
    private String quantidade;

    @CsvBindByPosition(position = 4)
    private String dataAquisicao;

    @CsvBindByPosition(position = 5)
    private String indicacao;
}
