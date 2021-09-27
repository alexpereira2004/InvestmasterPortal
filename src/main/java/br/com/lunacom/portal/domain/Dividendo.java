package br.com.lunacom.portal.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
