package br.com.lunacom.portal.validation.validator;

import br.com.lunacom.portal.util.DataUtil;
import br.com.lunacom.portal.validation.DataBrasil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeParseException;

public class DataBrasilValidator implements ConstraintValidator<DataBrasil, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isNotBlank(value)) {
            try {
                DataUtil util = new DataUtil();
                util.dataBrParaLocalDate(value);
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
