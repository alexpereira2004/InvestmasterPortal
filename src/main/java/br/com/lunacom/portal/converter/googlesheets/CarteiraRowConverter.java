package br.com.lunacom.portal.converter.googlesheets;

import br.com.lunacom.portal.domain.dto.googlesheets.CarteiraDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarteiraRowConverter implements GoogleSheetsRowConverter<CarteiraDto> {

    @Override
    public CarteiraDto convert(List<Object> row) {

        return CarteiraDto.builder()
            .codigoAtivo(row.get(0).toString())
//            .estrategia()
//            .precoPago()
//            .precoAtual()
//            .valorizacao()
//            .quantidade()
//            .totalInvestido()
//            .atualizacao()
//            .resultado()
//            .dataCompra()
            .build();
    }
}
