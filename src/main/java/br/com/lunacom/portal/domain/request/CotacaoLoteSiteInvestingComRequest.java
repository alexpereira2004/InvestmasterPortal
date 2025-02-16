package br.com.lunacom.portal.domain.request;

import br.com.lunacom.portal.domain.Ativo;
import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class CotacaoLoteSiteInvestingComRequest {
    @CsvBindByPosition(position = 0)
    private String dataReferencia;

    @CsvBindByPosition(position = 1)
    private String ultima;

    @CsvBindByPosition(position = 2)
    private String abertura;

    @CsvBindByPosition(position = 3)
    private String maxima;

    @CsvBindByPosition(position = 4)
    private String minima;

    private Ativo ativo;
}
