package br.com.lunacom.portal.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class MediaDividendosDto {
    private List<MediaDividendosValoresDto> total;
    private List<MediaDividendosValoresDto> acoes;
    private List<MediaDividendosValoresDto> fundos;
    private List<MediaDividendosValoresDto> outros;
}
