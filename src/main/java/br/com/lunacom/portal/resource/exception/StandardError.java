package br.com.lunacom.portal.resource.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mensagem;
    private String detalhe;
    private String local;
}
