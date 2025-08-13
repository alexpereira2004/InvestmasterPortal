package br.com.lunacom.portal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoFinanceiro extends BasicEntity<ProdutoFinanceiro> implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 100, nullable = false)
    private String instituicao;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_criacao", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCriacao;
}
