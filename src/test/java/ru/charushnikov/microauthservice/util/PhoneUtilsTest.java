package ru.charushnikov.microauthservice.util;

import org.junit.jupiter.api.Test;
import ru.charushnikov.microauthservice.exception.PhoneFormatException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.charushnikov.microauthservice.util.PhoneUtils.convertToStandardFormat;

public class PhoneUtilsTest {

    private final String STANDARD_PHONE = "+79273047003";

    @Test
    void convertToStandardFormat_startsWith_8_andOk() {
        String validPhone = "89273047003";
        assertEquals(STANDARD_PHONE, convertToStandardFormat(validPhone));
    }

    @Test
    void convertToStandardFormat_startsWith_7_andOk() {
        String validPhone = "79273047003";
        assertEquals(STANDARD_PHONE, convertToStandardFormat(validPhone));
    }

    @Test
    void convertToStandardFormat_startsWith_plus7_andOk() {
        String validPhone = STANDARD_PHONE;
        assertEquals(STANDARD_PHONE, convertToStandardFormat(validPhone));
    }


    @Test
    void convertToStandardFormat_contains10Digits_andStartsWith8_andException(){
        String invalidPhone = "8927305733";
        assertThrows(PhoneFormatException.class, () -> convertToStandardFormat(invalidPhone));
    }

    @Test
    void convertToStandardFormat_contains11Digits_andStartsNotWith_7_8_plus7_andException(){
        String invalidPhone = "19273057332";
        assertThrows(PhoneFormatException.class, () -> convertToStandardFormat(invalidPhone));
    }

    @Test
    void convertToStandardFormat_contains12Digits_andStartsNotWith_7_8_plus7_andException(){
        String invalidPhone = "192730573362";
        assertThrows(PhoneFormatException.class, () -> convertToStandardFormat(invalidPhone));
    }

    @Test
    void convertToStandardFormat_contains12Digits_andStartsWith_8_andException(){
        String invalidPhone = "192730573362";
        assertThrows(PhoneFormatException.class, () -> convertToStandardFormat(invalidPhone));
    }
}
