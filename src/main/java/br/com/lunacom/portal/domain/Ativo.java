package br.com.lunacom.portal.domain;

import br.com.lunacom.portal.converter.attribute.SeguindoConverter;
import br.com.lunacom.portal.converter.attribute.StatusConverter;
import br.com.lunacom.portal.domain.enumeration.Seguindo;
import br.com.lunacom.portal.domain.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ativo extends BasicEntity<Ativo> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String nomeCompleto;
    private String codigo;
    private String tipo;
    private String pais;
    private String caminho;
    @Convert(converter = SeguindoConverter.class)
    private Seguindo seguindo;
    private String cnpj;
    private LocalDateTime dataCriacao;
    @Convert(converter = StatusConverter.class)
    private Status status;
    private String observacao;

    @OneToMany(mappedBy = "ativo")
    private List<Cotacao> cotacoes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ativo_indice",
            joinColumns = @JoinColumn(name = "ativo_id"),
            inverseJoinColumns = @JoinColumn(name = "indice_id"))
    Set<Indice> indices;


}
