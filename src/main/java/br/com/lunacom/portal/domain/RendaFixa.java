package br.com.lunacom.portal.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RendaFixa extends BasicEntity<RendaFixa> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(precision = 15, scale = 2)
    private BigDecimal renda;

    @Column(precision = 15, scale = 2)
    private BigDecimal aplicado;

    @Column(precision = 5, scale = 2)
    private BigDecimal rentabilidade;

    @Column(precision = 5, scale = 2)
    private BigDecimal comparacao;

    @Column(precision = 5, scale = 2)
    private BigDecimal referenciaValor;

    @Column(name = "comparacao_referencia", length = 10)
    private String comparacaoReferencia;

    @Column(name = "data_referencia", length = 7)
    private String dataReferencia; // Exemplo: YYYY-MM

    @Column(name = "data_atualizacao", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime dataAtualizacao;

    @Column(name = "data_criacao", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produto_financeiro_id", nullable = false)
    private ProdutoFinanceiro produtoFinanceiro;
}
