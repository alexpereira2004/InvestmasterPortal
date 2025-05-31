package br.com.lunacom.portal.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StringParserTest {

    @Test
    void testToInteger_validInput() {
        assertEquals(123, StringParser.toInteger("123"));
    }

    @Test
    void testToInteger_invalidInput() {
        assertNull(StringParser.toInteger("abc"));
    }

    @Test
    @DisplayName("Deve converter string '123' para inteiro 123")
    void testToInteger_nullInput() {
        assertNull(StringParser.toInteger(null));
    }

    @Test
    void testToInteger_withDefault_valid() {
        assertEquals(42, StringParser.toInteger("42", 0));
    }

    @Test
    void testToInteger_withDefault_null() {
        assertEquals(99, StringParser.toInteger(null, 99));
    }

    @Test
    void testToDouble_validInput() {
        assertEquals(1234.56, StringParser.toDouble("1.234,56"));
    }

    @Test
    void testToDouble_nullInput() {
        assertEquals(0D, StringParser.toDouble(null));
    }

    @Test
    void testToDouble_emptyString() {
        assertEquals(0D, StringParser.toDouble(""));
    }

    @Test
    void testToDouble_withDefault_valid() {
        assertEquals(10.5, StringParser.toDouble("10,5", 0.0));
    }

    @Test
    @DisplayName("Deve aceitar um numero negativo")
    void testToDouble_deveAceitarValoresNegativos() {
        assertEquals(-10.5, StringParser.toDouble("-10,5"));
    }

    @Test
    @DisplayName("Deve aceitar um numero negativo")
    void testToDouble_deveAceitarStringVazia() {
        assertEquals(0, StringParser.toDouble(""));
    }

    @Test
    void testToDouble_withDefault_null() {
        assertEquals(7.7, StringParser.toDouble(null, 7.7));
    }

    @Test
    void testPrepareCsvFromRequest() throws UnsupportedEncodingException {
        String input = "valor%20=teste";
        String expected = "valor teste";
        assertEquals(expected, StringParser.prepareCsvFromRequest(input));
    }

    @Test
    void testToLocalDate_valid() {
        LocalDate expectedDate = LocalDate.of(2024, 5, 21);
        assertEquals(expectedDate, StringParser.toLocalDate("21.05.2024"));
    }
}
