package br.com.lunacom.portal.domain;

import br.com.lunacom.portal.domain.dto.AtivoDividendoDto;
import br.com.lunacom.portal.domain.dto.MediaDividendosValoresDto;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMapping(
        name = "MediaDividendosFundosDtoMapping",
        classes = @ConstructorResult(
                targetClass = MediaDividendosValoresDto.class,
                columns = {
                        @ColumnResult(name = "ano", type = String.class),
                        @ColumnResult(name = "valor_total", type = Double.class),
                        @ColumnResult(name = "media", type = Double.class),
                        @ColumnResult(name = "meses", type = Integer.class),
                }
        )
)
@NamedNativeQuery(
        name = "Dividendo.getMediaDividendosTotal",
        query = "SELECT u.ano, u.valor_total, u.media, u.meses FROM v_media_dividendos u ",
        resultSetMapping = "MediaDividendosFundosDtoMapping"
)

@NamedNativeQuery(
        name = "Dividendo.getMediaDividendosAcoes",
        query = "SELECT u.ano, u.valor_total, u.media, u.meses FROM v_media_dividendos_acoes u ",
        resultSetMapping = "MediaDividendosFundosDtoMapping"
)

@NamedNativeQuery(
        name = "Dividendo.getMediaDividendosFundos",
        query = "SELECT u.ano, u.valor_total, u.media, u.meses FROM v_media_dividendos_fundos u ",
        resultSetMapping = "MediaDividendosFundosDtoMapping"
)
@NamedNativeQuery(
        name = "Dividendo.getMediaDividendosOutros",
        query = "SELECT u.ano, u.valor_total, u.media, u.meses FROM v_media_dividendos_outros u ",
        resultSetMapping = "MediaDividendosFundosDtoMapping"
)

@SqlResultSetMapping(
        name = "AtivoDividendoMapping",
        classes = @ConstructorResult(
                targetClass = AtivoDividendoDto.class,
                columns = {
                        @ColumnResult(name = "codigo", type = String.class),
                        @ColumnResult(name = "valorTotal", type = BigDecimal.class),
                        @ColumnResult(name = "quantidadeMaxima", type = Integer.class),
                        @ColumnResult(name = "tipo", type = String.class),
                        @ColumnResult(name = "primeiroDividendo", type = LocalDate.class),
                        @ColumnResult(name = "ultimoDividendo", type = LocalDate.class)
                }
        )
)
@NamedNativeQuery(
        name = "Dividendo.getExtrato",
        query = "   SELECT a.codigo AS codigo, " +
                "          sum(d.valor_total) AS valorTotal, " +
                "          max(d.quantidade) AS quantidadeMaxima, " +
                "          a.tipo AS tipo, " +
                "          min(d.data_recebimento) AS primeiroDividendo, " +
                "          max(d.data_recebimento) AS ultimoDividendo " +
                "     FROM dividendo d " +
                "LEFT JOIN ativo a ON a.id = d.ativo_id " +
                "    WHERE a.codigo IN (:codigos) " +
                "      AND (:dataInicial IS NULL OR d.data_recebimento >= :dataInicial) " +
                "      AND (:dataFinal IS NULL OR d.data_recebimento <= :dataFinal) " +
                " GROUP BY a.codigo, DATE_FORMAT(data_recebimento, :periodicidade) " +
                " ORDER BY d.data_recebimento, d.tipo ",
        resultSetMapping = "AtivoDividendoMapping"
)

public class Dividendo extends BasicEntity<Dividendo>  {

    private LocalDate dataRecebimento;
    private String tipo;
    private Integer quantidade;
    private Double dividendo;
    private Double valorTotal;
    @ManyToOne
    @JoinColumn(name="ativo_id")
    @EqualsAndHashCode.Exclude
    private Ativo ativo;

}
