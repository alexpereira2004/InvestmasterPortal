package br.com.lunacom.portal.domain;

import br.com.lunacom.comum.domain.Ativo;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ativo_id")
    private Ativo ativo;

    private BigDecimal precoPago;
    private BigDecimal precoAtual;
    private BigDecimal valorizacao;
    private Integer quantidade;
    private BigDecimal totalInvestido;
    private BigDecimal totalAtualizado;
    private LocalDate dataCompra;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tags_carteira",
            joinColumns = @JoinColumn(name = "id_carteira"),
            inverseJoinColumns = @JoinColumn(name = "id_tag")
    )
    private Set<Tag> tags = new HashSet<>();

}
