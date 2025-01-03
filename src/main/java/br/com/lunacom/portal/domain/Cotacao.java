package br.com.lunacom.portal.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
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
