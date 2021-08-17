package br.com.lunacom.portal.validation;

import br.com.lunacom.portal.validation.validator.DataBrasilValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DataBrasilValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataBrasil {
    String message() default "A data informada deve estar no formato dd/mm/yyyy";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
