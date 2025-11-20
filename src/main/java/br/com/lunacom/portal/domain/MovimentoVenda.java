package br.com.lunacom.portal.domain;

import br.com.lunacom.comum.domain.Ativo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MovimentoVenda {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private LocalDate dataAquisicao;
    private Double precoPago;
    private Integer quantidade;
    private Double totalInvestido;

    private LocalDate dataVenda;
    private Double precoVenda;

    private Double totalFinal;
    private Double rendimento;

    @ManyToOne
    @JoinColumn(name="ativo_id")
    @EqualsAndHashCode.Exclude
    private Ativo ativo;
}
