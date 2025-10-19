package br.com.lunacom.portal.domain.entity.monitor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "monitor_regra")
public class MonitorRegra {

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

