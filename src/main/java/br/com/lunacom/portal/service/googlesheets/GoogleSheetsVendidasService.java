package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.comum.domain.Ativo;
import br.com.lunacom.comum.domain.MovimentoVenda;
import br.com.lunacom.comum.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import br.com.lunacom.comum.domain.dto.googlesheets.VendidasDto;
import br.com.lunacom.portal.converter.googlesheets.GoogleSheetsRowConverter;
import br.com.lunacom.portal.converter.googlesheets.VendidasRowConverter;
import br.com.lunacom.portal.service.AtivoService;
import br.com.lunacom.portal.service.MovimentoVendaService;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service("googlesheets-vendidas")
public class GoogleSheetsVendidasService implements GoogleSheetsDataServiceInterface<VendidasDto> {

    public static final String MSG_NAO_FOI_ENCONTRADO = "Ao salvar MovimentoVenda o código {} deveria existir mas não foi encontrado";
    private final VendidasRowConverter converter;

    private final MovimentoVendaService movimentoVendaService;
    private final AtivoService ativoService;
    @Value("${app.googleSheet.spreadsheetId}")
    private String spreadsheetId;
    @Value("${app.googleSheet.range.vendidas}")
    private String range;

    @Override
    public GoogleSheetsRowConverter<VendidasDto> getConverter() {
        return converter;
    }

    @Override
    public List<VendidasDto> lerPlanilha(LeituraPlanilhaRequestDto dto) throws IOException {
        final ValueRange valueRange = this.obterDados(dto);
        final List<VendidasDto> vendidasDtoList = convertAll(valueRange.getValues());

        if (Boolean.TRUE.equals(dto.getSave())) {

            Optional<LocalDate> dataUltimaVenda = movimentoVendaService.pesquisarUltimaDataCompra();

            final List<MovimentoVenda> result = vendidasDtoList.stream()
                    .map(this::converter)
                    .filter(i -> Objects.nonNull(i.getAtivo().getCodigo()))
                    .filter(aplicarFiltroPorMaiorData(dataUltimaVenda))
                    .collect(Collectors.toList());
            movimentoVendaService.removerUltimasVendas(dataUltimaVenda);
            result.forEach(movimentoVendaService::salvar);
        }

        return vendidasDtoList;
    }

    private Predicate<MovimentoVenda> aplicarFiltroPorMaiorData(Optional<LocalDate> maiorData) {
        return i -> {
            if (!maiorData.isPresent()) return true;

            return maiorData.isPresent() && Objects.nonNull(i.getDataVenda()) &&
                    (i.getDataVenda().isAfter(maiorData.get()) || i.getDataVenda().equals(maiorData.get()));
        };
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
                .totalFinal(item.getAtualizacao())
                .rendimento(item.getDiferenca())
                .ativo(ativo)
                .build();
    }

    @Override
    public String getSpreadsheetId() {
        return this.spreadsheetId;
    }

    @Override
    public String getRange() {
        return this.range;
    }
}
