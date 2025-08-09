package br.com.lunacom.portal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AppProperties {

    @Value("${app.credentials-file-path}")
    private String credentialsFilePath;

    public static String CREDENTIALS_FILE_PATH;

    @PostConstruct
    public void init() {
        CREDENTIALS_FILE_PATH = credentialsFilePath;
    }
}
