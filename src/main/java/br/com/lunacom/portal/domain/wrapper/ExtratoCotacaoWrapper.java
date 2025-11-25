package br.com.lunacom.portal.domain.wrapper;

import br.com.lunacom.comum.domain.dto.ExtratoCotacaoDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
public class ExtratoCotacaoWrapper {
    private Map<String, List<ExtratoCotacaoDto>> cotacoes;
}
