package br.com.lunacom.portal.converter.attribute;

import br.com.lunacom.portal.domain.enumeration.Status;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        return status != null ? status.getCodigo() : null;
    }

    @Override
    public Status convertToEntityAttribute(String codigo) {
        return codigo != null ? Status.fromCodigo(codigo) : null;
    }
}
