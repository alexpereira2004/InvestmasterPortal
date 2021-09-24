package br.com.lunacom.portal.service;

import br.com.lunacom.portal.repository.DividendoRepository;
import br.com.lunacom.portal.util.DataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class DividendoService {
    private final DataUtil dataUtil;
    private final DividendoRepository repository;

    public void salvarHtml(String request) {
        String regex = "<div class=\"cont-date settlement\">(.*)<\\/div>\\n\\s*<div class=\"cont-description\">(.*)<\\/div>\\n\\s*<div class=\"cont-value\">\\n\\s*<span>(.*)<\\/span>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request);
        while (matcher.find()) {
            System.out.println("group 1: " + matcher.group(1));
            System.out.println("group 2: " + matcher.group(2));
            System.out.println("group 3: " + matcher.group(3));
        }
    }

}
