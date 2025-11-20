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
@Table(name = "mr_volume")
public class MonitorRegraVolume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "volume_minimo")
    private Integer volumeMinimo;

    @Column(name = "media_dias")
    private Integer mediaDias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_regra")
    private Monitor regra;

}
