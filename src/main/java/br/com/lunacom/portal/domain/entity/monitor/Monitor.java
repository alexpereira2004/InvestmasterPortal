package br.com.lunacom.portal.domain.entity.monitor;

import br.com.lunacom.portal.domain.Ativo;
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
@Table(name = "monitor")
public class Monitor extends BasicEntity<Monitor> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ativo_id", referencedColumnName = "id")
    private Ativo ativo;

    private Integer prioridade;

    private String status;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;


}

