package br.com.lunacom.portal.domain.request;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class MovimentoVendaCsvRequest {
    @CsvBindByPosition(position = 0)
    private String ativoCodigo;

    @CsvBindByPosition(position = 1)
    private String dataAquisicao;

    @CsvBindByPosition(position = 2)
    private String precoPago;

    @CsvBindByPosition(position = 3)
    private String quantidade;

    @CsvBindByPosition(position = 4)
    private String dataVenda;

    @CsvBindByPosition(position = 5)
    private String precoVenda;

}