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
@Table(name = "monitor_regra_cruzamento_media")
public class MonitorRegraCruzamentoMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "media_curta")
    private Integer mediaCurta;

    @Column(name = "media_longa")
    private Integer mediaLonga;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_regra")
    private MonitorRegra regra;
}
