package br.com.lunacom.portal.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AtivoResponse extends GenericResponse {
    private Integer id;
    private String nome;
    private String nomeCompleto;
    private String codigo;
    private String cnpj;
    private String tipo;
    private String tipoDescricao;
    private String pais;
    private String caminho;
    private String seguindo;
    private String seguindoDescricao;
}
