package br.com.lunacom.portal.domain;

import br.com.lunacom.comum.domain.GenericEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@Access(AccessType.FIELD)
public abstract class BasicEntity<T> implements Serializable, GenericEntity<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(updatable = false)
    private LocalDateTime dataCriacao;

    @Column
    private LocalDateTime dataAtualizacao;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    @Override
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    @Override
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
