package br.com.lunacom.portal.service.googlesheets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ServiceFactory {

    private final Map<String, GoogleSheetsDataServiceInterface<?>> services;

    @Autowired
    public ServiceFactory(List<GoogleSheetsDataServiceInterface<?>> serviceList) {
        this.services = serviceList.stream().collect(Collectors.toMap(
                s -> s.getClass().getAnnotation(Service.class).value(),
                s -> s
        ));
    }

    @SuppressWarnings("unchecked")
    public <T> GoogleSheetsDataServiceInterface<T> getService(String type) {
        GoogleSheetsDataServiceInterface<T> service = (GoogleSheetsDataServiceInterface<T>) services.get(type);
        if (service == null) {
            throw new IllegalArgumentException("Nenhuma service encontrada para o tipo: " + type);
        }
        return service;
    }
}
