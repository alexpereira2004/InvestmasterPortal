package br.com.lunacom.portal.domain;

import br.com.lunacom.portal.domain.dto.CotacaoAgoraDto;
import br.com.lunacom.portal.domain.dto.CotacaoHistoricoDto;
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


@SqlResultSetMapping(
        name = "CotacaoHistoricoDto",
        classes = @ConstructorResult(
                targetClass = CotacaoHistoricoDto.class,
                columns = {
                        @ColumnResult(name = "id", type = String.class),
                        @ColumnResult(name = "nome_ativo", type = String.class),
                        @ColumnResult(name = "codigo", type = String.class),
                        @ColumnResult(name = "primeiro_preco", type = BigDecimal.class),
                        @ColumnResult(name = "ultimo_preco", type = BigDecimal.class),
                        @ColumnResult(name = "variacao", type = BigDecimal.class),
                        @ColumnResult(name = "preco_medio", type = BigDecimal.class),
                        @ColumnResult(name = "up_pm", type = BigDecimal.class),
                        @ColumnResult(name = "referencia_inicio", type = LocalDate.class),
                        @ColumnResult(name = "referencia_final", type = LocalDate.class),
                        @ColumnResult(name = "quantidade_leituras", type = Integer.class),
                        @ColumnResult(name = "preco_minimo_periodo", type = BigDecimal.class),
                        @ColumnResult(name = "preco_maximo_periodo", type = BigDecimal.class),
                        @ColumnResult(name = "data_importacao", type = LocalDateTime.class)
                }
        )
)
@NamedNativeQuery(
        name = "Cotacao.pesquisarHistorico",
        query = "                SELECT aid AS id, " +
                "                       nome AS nome_ativo, " +
                "                       codigo,\n" +
                "                       pri_c.preco AS primeiro_preco,\n" +
                "                       ult_c.preco AS ultimo_preco, \n" +
                "                       round(((ult_c.preco * 100 ) / pri_c.preco ) - 100, 2) AS variacao,\n" +
                "                       media AS preco_medio,\n" +
                "                       ult_c.preco - media AS \"up_pm\",\n" +
                "                       pri_c.referencia AS referencia_inicio,\n" +
                "                       ult_c.referencia AS referencia_final, \n" +
                "                       cotacoes AS quantidade_leituras, \n" +
                "                       preco_minimo_periodo, \n" +
                "                       preco_maximo_periodo,\n" +
                "                       tmp.importacao AS data_importacao\n" +
                "                  FROM (\n" +
                "                            SELECT a.id aid, a.nome, a.codigo,\n" +
                "                                   c.abertura, c.preco,\n" +
                "                                   round(sum(c.preco)/count(1),2) AS media,\n" +
                "                                   min(c.referencia) min_referencia, \n" +
                "                                   max(c.referencia) AS max_referencia, \n" +
                "                                   count(1) AS cotacoes, \n" +
                "                                   max(c.preco) AS preco_maximo_periodo,\n" +
                "                                   min(c.preco) AS preco_minimo_periodo,\n" +
                "                                   c.importacao\n" +
                "                              FROM ativo a \n" +
                "                        LEFT JOIN cotacao c ON a.id = c.ativo_id\n" +
                "                             WHERE (:dataInicio IS NULL OR c.referencia >= :dataInicio ) \n" +
                "                               AND (:dataFinal IS NULL OR c.referencia < :dataFinal ) \n" +
                "                               AND (:tipoAtivo IS NULL OR a.tipo = :tipoAtivo ) \n" +
                "                               AND (:ativos IS NULL OR a.codigo IN (:ativos)) \n" +
                "                          GROUP BY a.id, a.nome, a.codigo \n" +
                "                    ) tmp\n" +
                "            INNER JOIN cotacao pri_c ON aid = pri_c.ativo_id AND pri_c.referencia = min_referencia \n" +
                "            INNER JOIN cotacao ult_c ON aid = ult_c.ativo_id AND ult_c.referencia = max_referencia\n" +
                "                WHERE 1=1\n" +
                "                AND cotacoes > 1\n" +
                "              ORDER BY cotacoes, ult_c.referencia DESC",
        resultSetMapping = "CotacaoHistoricoDto"
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
