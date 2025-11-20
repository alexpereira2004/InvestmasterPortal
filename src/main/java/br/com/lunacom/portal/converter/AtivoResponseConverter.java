package br.com.lunacom.portal.converter;

import br.com.lunacom.comum.domain.Ativo;
import br.com.lunacom.comum.domain.enumeration.AcaoTipo;
import br.com.lunacom.portal.domain.response.AtivoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AtivoResponseConverter implements Converter<AtivoResponse, Ativo> {

    @Override
    public Ativo encode(AtivoResponse input) {
        return null;
    }

    @Override
    public AtivoResponse decode(Ativo input) {
        return AtivoResponse.builder()
                .id(input.getId())
                .nome(input.getNome())
                .nomeCompleto(input.getNomeCompleto())
                .codigo(input.getCodigo())
                .cnpj(input.getCnpj())
                .tipo(input.getTipo())
                .tipoDescricao(AcaoTipo.fromCodigo(input.getTipo()).getDescricao())
                .pais(input.getPais())
                .caminho(input.getCaminho())
                .seguindo(input.getSeguindo().getCodigo())
                .seguindoDescricao(input.getSeguindo().getDescricao())
                .build();
    }
}
