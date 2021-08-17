package br.com.lunacom.portal.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MovimentoCompra implements Serializable, GenericEntity<MovimentoCompra> {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private LocalDate dataAquisicao;
    private Double precoPago;
    private Integer quantidade;
    private Double totalInvestido;
    private String indicacao;
    private String estrategia;
    @ManyToOne
    @JoinColumn(name="ativo_id")
    @EqualsAndHashCode.Exclude
    private Ativo ativo;

    @Override
    public LocalDateTime getDataCriacao() {
        return null;
    }

    @Override
    public void setDataCriacao(LocalDateTime dataCriacao) {

    }

    @Override
    public LocalDateTime getDataAtualizacao() {
        return null;
    }

    @Override
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {

    }
}
