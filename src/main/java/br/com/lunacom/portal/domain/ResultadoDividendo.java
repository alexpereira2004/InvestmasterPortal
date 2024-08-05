package br.com.lunacom.portal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
