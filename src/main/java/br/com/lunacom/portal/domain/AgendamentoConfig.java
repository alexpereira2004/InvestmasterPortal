package br.com.lunacom.portal.domain;

import br.com.lunacom.portal.converter.attribute.StatusConverter;
import br.com.lunacom.portal.domain.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "agendamento_config")
public class AgendamentoConfig {
    @Id
    private Integer id;
    private String nome;
    private String cron;
    @Convert(converter = StatusConverter.class)
    private Status status;
}
