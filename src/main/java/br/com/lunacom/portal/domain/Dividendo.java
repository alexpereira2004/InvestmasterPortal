package br.com.lunacom.portal.domain;

import br.com.lunacom.portal.domain.dto.MediaDividendosValoresDto;
import lombok.*;

import javax.persistence.*;
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
