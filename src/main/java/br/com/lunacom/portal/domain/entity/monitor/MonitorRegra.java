package br.com.lunacom.portal.domain.entity.monitor;

import br.com.lunacom.portal.domain.BasicEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "monitor_regra")
public class MonitorRegra extends BasicEntity<MonitorRegra> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ativo_id")
    private Integer ativoId;

    private Integer prioridade;

    private String status;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;


}

