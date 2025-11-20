package br.com.lunacom.portal.domain;

import br.com.lunacom.comum.domain.enumeration.Status;
import br.com.lunacom.portal.converter.attribute.StatusConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
