package br.com.lunacom.portal.resource.v1.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = ResourceAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public StandardError resourceAccessException(ResourceAccessException e, HttpServletRequest request) {
        return new StandardError("Recurso não disponível", e.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ValidationError validation(MethodArgumentNotValidException e, HttpServletRequest request) {

        List<String> list = new ArrayList<>();
        ValidationError err = new ValidationError(
                "Verifique os seguintes itens antes de avançar",
                request.getRequestURI());
        for (ObjectError x : e.getBindingResult().getAllErrors()) {
            list.add(String.format("%s", x.getDefaultMessage()));
        }
        err.setDetalhe(list);
        return err;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    public final ValidationError handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        List<String> details = ex.getConstraintViolations()
                .parallelStream()
                .map(v -> String.format("%s. (Atributo: '%s', valor informado: %s)", v.getMessage(), v.getPropertyPath(), v.getInvalidValue()))
                .collect(Collectors.toList());
        return new ValidationError("É necessário revisar a requisição", details, request.getRequestURI());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    public final ValidationError handleConstraintViolation(HttpMessageNotReadableException ex, HttpServletRequest request) {
        final HttpInputMessage httpInputMessage = ex.getHttpInputMessage();
        final String detail = String.format("É necessário revisar a requisição: %s", ex.getMessage());
        return new ValidationError(detail, request.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    public final StandardError handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        final String mensagem = String.format("Ocorreu um erro relacionado a integridade dos dados");
        final String detalhe = ex.getMessage();
        return new StandardError(mensagem, detalhe, request.getRequestURI());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public StandardError exception(Exception e, HttpServletRequest request) {
        return new StandardError("Erro global", e.getMessage(), request.getRequestURI());
    }
}
