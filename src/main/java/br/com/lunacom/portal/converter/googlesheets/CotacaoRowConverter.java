package br.com.lunacom.portal.converter.googlesheets;

import br.com.lunacom.portal.domain.dto.googlesheets.CotacaoDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CotacaoRowConverter implements GoogleSheetsRowConverter<CotacaoDto> {

    @Override
    public CotacaoDto convert(List<Object> data) {
        if (data == null || data.isEmpty() || data.size() < 2) {
            throw new IllegalArgumentException("A lista de dados está vazia ou inválida.");
        }

        return CotacaoDto.builder()
                .codigo(data.get(0).toString())
                .cotacao(data.get(1).toString())
                .build();
    }
}
