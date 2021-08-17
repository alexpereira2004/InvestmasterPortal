package br.com.lunacom.portal.converter;

import br.com.lunacom.portal.domain.GenericEntity;
import br.com.lunacom.portal.domain.request.GenericRequest;

public abstract class GenericConverter<
        R extends GenericRequest,
        T extends GenericEntity> implements Converter<R, T> {
}
