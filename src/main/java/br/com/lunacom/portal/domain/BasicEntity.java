package br.com.lunacom.portal.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
@Data
public abstract class BasicEntity<T> implements Serializable, GenericEntity<T>{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    LocalDate dataCriacao;
    LocalDate dataAtualizacao;
}
