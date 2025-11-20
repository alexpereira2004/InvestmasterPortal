package br.com.lunacom.portal.domain.entity.monitor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mr_queda_percentual")
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
    private Monitor regra;
}
