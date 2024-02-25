package br.com.lunacom.portal.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AtivoResponse extends GenericResponse {
    private Integer id;
    private String nome;
    private String nome_completo;
    private String codigo;
    private String tipo;
    private String pais;
    private String caminho;
}
