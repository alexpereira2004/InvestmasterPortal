package br.com.lunacom.portal.converter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface OneWayConverter<I, O> {
    O encode(I input);

    default List<O> encode(Collection<I> input) {
        return input.stream()
                .map(this::encode)
                .collect(Collectors.toList());
    }
}
