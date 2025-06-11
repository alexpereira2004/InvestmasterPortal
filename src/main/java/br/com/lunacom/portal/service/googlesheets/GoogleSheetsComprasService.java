package br.com.lunacom.portal.service.googlesheets;

import br.com.lunacom.portal.converter.googlesheets.ComprasRowConverter;
import br.com.lunacom.portal.converter.googlesheets.GoogleSheetsRowConverter;
import br.com.lunacom.portal.domain.Ativo;
import br.com.lunacom.portal.domain.MovimentoCompra;
import br.com.lunacom.portal.domain.TipoAtivoInterface;
import br.com.lunacom.portal.domain.dto.googlesheets.CarteiraDto;
import br.com.lunacom.portal.domain.dto.googlesheets.LeituraPlanilhaRequestDto;
import br.com.lunacom.portal.domain.enumeration.Status;
import br.com.lunacom.portal.service.AtivoService;
import br.com.lunacom.portal.service.MovimentoCompraService;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public abstract class GoogleSheetsComprasService
        implements GoogleSheetsDataServiceInterface<CarteiraDto>, TipoAtivoInterface {

    public static final String MSG_ERRO = "A leitura não vai resultar nenhum item novo pois algum dos marcadores não foi encontrado (\"{}\" ou \"{}\")";
    public static final String MSG_NAO_FOI_ENCONTRADO = "Ao salvar MovimentoCompra o código {} deveria existir mas não foi encontrado. Será criado um novo ativo com restrição";
    public static final String NOVO_ATIVO_AUTOMATICO = "Ativo criado automaticamente com restricao ao inserir novo Movimento de Compra";

    protected abstract String getMarcadorDeInicio();
    protected abstract String getMarcadorDeFinal();

    private final ComprasRowConverter converter;
    private final MovimentoCompraService movimentoCompraService;
    private final AtivoService ativoService;

    @Override
    public GoogleSheetsRowConverter<CarteiraDto> getConverter() {
        return converter;
    }

    @Override
    public List<CarteiraDto> lerPlanilha(LeituraPlanilhaRequestDto dto) throws IOException {
        final ValueRange valueRange = this.obterDados(dto);
        final List<CarteiraDto> compras = convertAll(valueRange.getValues());

        List<CarteiraDto> comprasEfetivasList = filtrarPorMarcadores(compras);
        comprasEfetivasList = comprasEfetivasList.stream()
                .filter(e -> !e.getQuantidade().equals(0))
                .collect(Collectors.toList());

        if (dto.getSave()) {
            final List<MovimentoCompra> entityList = comprasEfetivasList.stream()
                    .map(i -> this.converter(i))
                    .collect(Collectors.toList());
            entityList.forEach(i -> movimentoCompraService.salvar(i));
        }

        return comprasEfetivasList;
    }

    private List<CarteiraDto> filtrarPorMarcadores(List<CarteiraDto> lista) {
        int inicio = -1;
        int fim = lista.size();

        for (int i = 0; i < lista.size(); i++) {
            String header = lista.get(i).getHeader();
            if (getMarcadorDeInicio().equals(header)) {
                inicio = i + 2;
            }
            String codigo = lista.get(i).getCodigoAtivo();
            if (getMarcadorDeFinal().equals(codigo) && inicio != -1) {
                fim = i;
                break;
            }
        }

        if (inicio != -1 && inicio < fim) {
            return lista.subList(inicio, fim);
        }

        log.error(MSG_ERRO, getMarcadorDeInicio(), getMarcadorDeFinal());
        return Collections.emptyList();
    }

    protected MovimentoCompra converter(CarteiraDto item) {
        Ativo ativo = ativoService
                .pesquisarPorCodigo(item.getCodigoAtivo())
                .orElse(new Ativo());
        if (Objects.isNull(ativo.getCodigo())) {
            log.warn(MSG_NAO_FOI_ENCONTRADO, item.getCodigoAtivo());
            ativo = this.salvarAtivo(item.getCodigoAtivo());
        }
        final MovimentoCompra compra = MovimentoCompra.builder()
                .dataAquisicao(item.getDataCompra())
                .precoPago(item.getPrecoPago())
                .quantidade(item.getQuantidade())
                .totalInvestido(item.getTotalInvestido())
//                .indicacao(item.get)
//                .estrategia()
                .ativo(ativo)
                .build();
        return compra;
    }

    private Ativo salvarAtivo(String codigo) {
        final Ativo a = Ativo.builder()
                .codigo(codigo)
                .seguindo("5")
                .dataCriacao(LocalDateTime.now())
                .tipo(getTipoAtivo())
                .status(Status.RESTRICAO.getCodigo())
                .observacao(NOVO_ATIVO_AUTOMATICO)
                .build();
        return ativoService.salvar(a);
    }

}
