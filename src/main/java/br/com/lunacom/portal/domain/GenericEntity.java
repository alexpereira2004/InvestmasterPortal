package br.com.lunacom.portal.domain;

import java.time.LocalDateTime;

public interface GenericEntity<T> {
    public Integer getId();

    public void setId(Integer id);

    public LocalDateTime getDataCriacao();

    public void setDataCriacao(LocalDateTime dataCriacao);

    public LocalDateTime getDataAtualizacao();

    public void setDataAtualizacao(LocalDateTime dataAtualizacao);
}
