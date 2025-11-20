package br.com.lunacom.portal.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "v_resultado_dividendo")
public class ResultadoDividendo {
    @Id
    private String data;
    private Integer ano;
    private Integer mes;
    private Double fundos;
    private Double acoes;
    private Double outros;
    private Double total;
    private Integer acaoId;
    private Integer fundosId;
    private Integer bdrId;
}
