package br.com.lunacom.portal.domain;

import br.com.lunacom.portal.domain.dto.ExtratoCotacaoDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMapping(
        name = "ExtratoCotacaoDtoMapping",
        classes = @ConstructorResult(
                targetClass = ExtratoCotacaoDto.class,
                columns = {
                        @ColumnResult(name = "codigo ", type = String.class),
                        @ColumnResult(name = "nome", type = String.class),
                        @ColumnResult(name = "preco", type = Double.class),
                        @ColumnResult(name = "variacao", type = Double.class),
                        @ColumnResult(name = "referencia", type = LocalDate.class),
                        @ColumnResult(name = "importacao", type = LocalDateTime.class)
                }
        )
)
@NamedNativeQuery(
        name = "Cotacao.extrato",
        query = "SELECT a.codigo, \n" +
                "\t        a.nome, \n" +
                "\t        c.preco, \n" +
                "\t        c.variacao, \n" +
                "\t        c.referencia,\n" +
                "\t        c.importacao \n" +
                "       FROM cotacao AS c\n" +
                " INNER JOIN ativo a ON a.id = c.ativo_id\n" +
                "      WHERE 1=1\n" +
                "\t        AND a.codigo IN (:codigoLista)\n" +
                "\t      AND (:dataInicio IS NULL OR c.referencia >= :dataInicio) " +
                "\t      AND (:dataFim IS NULL OR c.referencia <= :dataFim) " +
                "\t   ORDER BY a.codigo, c.referencia DESC ",
        resultSetMapping = "ExtratoCotacaoDtoMapping"
)

@SqlResultSetMapping(
        name = "CotacaoAgoraDtoMapping",
        classes = @ConstructorResult(
                targetClass = CotacaoAgoraDto.class,
                columns = {
                        @ColumnResult(name = "codigo", type = String.class),
                        @ColumnResult(name = "nome", type = String.class),
                        @ColumnResult(name = "cotacao_atual", type = BigDecimal.class),
                        @ColumnResult(name = "variacao_periodo", type = BigDecimal.class),
                        @ColumnResult(name = "dividendos_ano_anterior", type = BigDecimal.class),
                        @ColumnResult(name = "dy_percentual", type = BigDecimal.class),
                        @ColumnResult(name = "data_cotacao", type = LocalDate.class),
                        @ColumnResult(name = "data_importacao", type = LocalDate.class)
                }
        )
)
@NamedNativeQuery(
        name = "Cotacao.pesquisarCotacaoAgora",
        query = "SELECT codigo, \n" +
                "       nome, \n" +
                "       cotacao_atual, \n" +
                "       variacao_periodo, \n" +
                "       dividendos_ano_anterior, \n" +
                "       dy_percentual, \n" +
                "       `data` AS data_cotacao, \n" +
                "       data_importacao \n" +
                "FROM v_cotacao_agora",
        resultSetMapping = "CotacaoAgoraDtoMapping"
)

public class Cotacao implements Serializable, Comparable<Cotacao> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Integer id;

    private Double preco;
    private Double abertura;
    private Double variacao;
    private Double maxima;
    private Double minima;

    @EqualsAndHashCode.Exclude
    private LocalDateTime importacao;
    private LocalDate referencia;
    private String volume;

    @EqualsAndHashCode.Exclude
    private String origem;

    @ManyToOne
    @JoinColumn(name="ativo_id")
    @EqualsAndHashCode.Exclude
    private Ativo ativo;

    @Override
    public int compareTo(Cotacao o) {
        if (this.getReferencia() == null || o.getReferencia() == null)
            return 0;
        return getReferencia().compareTo(o.getReferencia());
    }
}
