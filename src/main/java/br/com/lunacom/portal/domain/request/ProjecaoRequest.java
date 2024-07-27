package br.com.lunacom.portal.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Component
public class ProjecaoRequest extends GenericRequest {
    private Integer id;
    private Integer ano;
    private List<Integer> anoLista;
    private Integer mes;
    private String tipo;
    private List<String> tipoLista;
    private Double valor;
    private Double valorAlcancado;
    private Boolean efetivado;
    private String observacao;
    private Boolean totalizador;
}
