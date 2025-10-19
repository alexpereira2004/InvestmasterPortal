package br.com.lunacom.portal.domain.entity.monitor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "monitor_regra_queda_percentual")
public class MonitorRegraQuedaPercentual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "percentual_queda")
    private Double percentualQueda;

    @Column(name = "periodo_minutos")
    private Integer periodoMinutos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_regra")
    private MonitorRegra regra;
}
