package br.com.lunacom.portal.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MovimentoCompra {
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
}
