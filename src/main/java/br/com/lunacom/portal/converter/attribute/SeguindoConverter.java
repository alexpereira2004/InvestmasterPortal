package br.com.lunacom.portal.converter.attribute;

import br.com.lunacom.portal.domain.enumeration.Seguindo;

import javax.persistence.AttributeConverter;

public class SeguindoConverter implements AttributeConverter<Seguindo, String> {

    @Override
    public String convertToDatabaseColumn(Seguindo attribute) {
        return attribute != null ? attribute.getCodigo() : null;
    }

    @Override
    public Seguindo convertToEntityAttribute(String dbData) {
        return Seguindo.fromCodigo(dbData);
    }
}
