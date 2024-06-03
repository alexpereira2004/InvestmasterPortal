package br.com.lunacom.portal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
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
    private String seguindo;
    private String cnpj;

    @OneToMany(mappedBy = "ativo")
    private List<Cotacao> cotacoes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ativo_indice",
            joinColumns = @JoinColumn(name = "ativo_id"),
            inverseJoinColumns = @JoinColumn(name = "indice_id"))
    Set<Indice> indices;


}
