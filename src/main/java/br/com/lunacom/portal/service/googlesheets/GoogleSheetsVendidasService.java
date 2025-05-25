package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.GoogleSheetsRowConverter;
import br.com.lunacom.portal.converter.googlesheets.VendidasRowConverter;
import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.MovimentoVenda;
import br.com.lunacom.portal.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import br.com.lunacom.portal.domain.dto.googlesheets.VendidasDto;
import br.com.lunacom.portal.service.AtivoService;
import br.com.lunacom.portal.service.MovimentoVendaService;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service("googlesheets-vendidas")
public class GoogleSheetsVendidasService implements GoogleSheetsDataServiceInterface<VendidasDto> {

    public static final String MSG_NAO_FOI_ENCONTRADO = "Ao salvar MovimentoVenda o código {} deveria existir mas não foi encontrado";
    private final VendidasRowConverter converter;

    private final MovimentoVendaService movimentoVendaService;
    private final AtivoService ativoService;

    @Override
    public GoogleSheetsRowConverter<VendidasDto> getConverter() {
        return converter;
    }

    @Override
    public List<VendidasDto> lerPlanilha(LeituraPlanilhaRequestDto dto) throws IOException {
        final ValueRange valueRange = this.obterDados(dto);
        final List<VendidasDto> vendidasDtoList = convertAll(valueRange.getValues());

        if (dto.getSave()) {
//            @TODO - Aplicar lógica para não salvar de forma duplicada
            vendidasDtoList.stream()
                    .map(i -> this.converter(i))
                    .filter(i -> Objects.nonNull(i.getAtivo().getCodigo()))
                    .collect(Collectors.toList())
                    .forEach(i -> movimentoVendaService.salvar(i));
//            carteiraService.removerPorCodigoAtivo(identificarAtivosDescontinuados(carteiraDtoLimpa));
        }

        return vendidasDtoList;
    }

    private MovimentoVenda converter(VendidasDto item) {

        final Ativo ativo = ativoService
                .pesquisarPorCodigo(item.getCodigoAtivo())
                .orElse(new Ativo());
        if (Objects.isNull(ativo.getCodigo())) {
            log.warn(MSG_NAO_FOI_ENCONTRADO, item.getCodigoAtivo());
        }

        return MovimentoVenda.builder()
                .dataAquisicao(item.getDataCompra())
                .precoPago(item.getPrecoPago())
                .quantidade(item.getQuantidade())
                .totalInvestido(item.getTotalInvestido())
                .dataVenda(item.getDataVenda())
                .precoVenda(item.getPrecoFinal())
                .totalFinal(item.getTotalInvestido())
                .rendimento(item.getRendimento())
                .ativo(ativo)
                .build();
    }
}
