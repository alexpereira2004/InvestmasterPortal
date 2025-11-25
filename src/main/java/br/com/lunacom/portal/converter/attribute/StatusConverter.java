package br.com.lunacom.portal.converter.attribute;


import br.com.lunacom.comum.domain.enumeration.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

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
