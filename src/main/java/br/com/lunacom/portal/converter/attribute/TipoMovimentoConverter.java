package br.com.lunacom.portal.converter.attribute;

import br.com.lunacom.portal.domain.enumeration.TipoMovimento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoMovimentoConverter implements AttributeConverter<TipoMovimento, String> {
    @Override
    public String convertToDatabaseColumn(TipoMovimento attribute) {
        return attribute != null ? attribute.getCodigo() : null;
    }

    @Override
    public TipoMovimento convertToEntityAttribute(String dbData) {
        return dbData != null ? TipoMovimento.fromCodigo(dbData) : null;
    }
}
