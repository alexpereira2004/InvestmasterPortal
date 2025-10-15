package br.com.lunacom.portal.domain.entity.monitor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    // ====== RELACIONAMENTOS ======

    @OneToMany(mappedBy = "regra", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MonitorRegraQuedaPercentual> regrasQuedaPercentual = new HashSet<>();

    @OneToMany(mappedBy = "regra", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MonitorRegraVolume> regrasVolume = new HashSet<>();

    @OneToMany(mappedBy = "regra", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MonitorRegraCruzamentoMedia> regrasCruzamentoMedia = new HashSet<>();


    // ====== MÉTODOS AUXILIARES ======

    public void addRegraQueda(MonitorRegraQuedaPercentual regra) {
        regrasQuedaPercentual.add(regra);
        regra.setRegra(this);
    }

    public void addRegraVolume(MonitorRegraVolume regra) {
        regrasVolume.add(regra);
        regra.setRegra(this);
    }

    public void addRegraCruzamento(MonitorRegraCruzamentoMedia regra) {
        regrasCruzamentoMedia.add(regra);
        regra.setRegra(this);
    }

}

