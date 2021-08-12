package br.com.lunacom.portal.domain.dto;

import com.opencsv.bean.CsvBindByPosition;

public class MovimentoCompraCsvRequest {
    @CsvBindByPosition(position = 0)
    private String dataAquisicao;

    @CsvBindByPosition(position = 1)
    private String precoPago;

    @CsvBindByPosition(position = 2)
    private String quantidade;

    @CsvBindByPosition(position = 3)
    private String indicacao;

    @CsvBindByPosition(position = 4)
    private String estrategia;

    @CsvBindByPosition(position = 5)
    private String ativoCodigo;
}
