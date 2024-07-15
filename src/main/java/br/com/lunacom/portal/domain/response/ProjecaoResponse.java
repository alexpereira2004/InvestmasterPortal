package br.com.lunacom.portal.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjecaoResponse extends GenericResponse {
    private Integer id;

    private Integer ano;
    private Integer mes;
    private String tipo;
    private Double valor;
    private Double valorAlcancado;
    private Boolean efetivado;
    private String observacao;
}
