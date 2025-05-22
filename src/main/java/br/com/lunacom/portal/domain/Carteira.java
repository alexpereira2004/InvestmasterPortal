package br.com.lunacom.portal.domain;

import lombok.*;

import javax.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ativo_id")
    private Ativo ativo;

    private Double precoPago;
    private Double precoAtual;
    private Double valorizacao;
    private Integer quantidade;
    private Double totalInvestido;
    private Double totalAtualizado;
    private LocalDate dataCompra;

    @ManyToMany
    @JoinTable(
            name = "tags_carteira",
            joinColumns = @JoinColumn(name = "id_carteira"),
            inverseJoinColumns = @JoinColumn(name = "id_tag")
    )
    private Set<Tag> tags = new HashSet<>();

}
