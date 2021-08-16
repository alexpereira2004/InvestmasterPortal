package br.com.lunacom.portal.resource.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationError {
	private static final long serialVersionUID = 1L;

	private String mensagem;
	private List<String> detalhe;
	private String local;

	public ValidationError(String mensagem, String local) {
		this.mensagem = mensagem;
		this.local = local;
	}
}
