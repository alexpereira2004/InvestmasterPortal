package br.com.lunacom.portal.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;

@AllArgsConstructor
@Getter
public enum Meses {
    JAN("01","Jan", "Janeiro", "",""),
    FEV("02","Fev","Fevereiro", "",""),
    MAR("03","Mar","Março", "",""),
    ABR("04","Abr","Abril", "",""),
    MAI("05","Mai","Maio", "",""),
    JUN("06","Jun","Junho", "",""),
    JUL("07","Jul","Julho", "",""),
    AGO("08","Ago","Agosto", "",""),
    SET("09","Set","Setembro", "",""),
    OUT("10","Out","Outubro", "",""),
    NOV("11","Nov","Novembro", "",""),
    DEZ("12","Dez","Dezembro", "","");

    private final String codigo;
    private final String SiglaPt;
    private final String DescricaoPt;
    private final String SiglaEn;
    private final String DescricaoEn;

    public static Meses fromSiglaPt(String value) {
        return EnumSet.allOf(Meses.class)
                .stream()
                .filter(it -> it.getSiglaPt().equals(value))
                .findFirst()
                .orElse(JAN);
    }

    public static Meses fromDescricao(String value) {
        return EnumSet.allOf(Meses.class)
                .stream()
                .filter(it -> it.getDescricaoPt().equals(value))
                .findFirst()
                .orElse(JAN);
    }

    public static Meses fromCodigo(String value) {
        return EnumSet.allOf(Meses.class)
                .stream()
                .filter(it -> it.getCodigo().equals(value))
                .findFirst()
                .orElse(JAN);
    }
}
