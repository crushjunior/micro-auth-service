package ru.charushnikov.microauthservice.util;

import ru.charushnikov.microauthservice.exception.PhoneFormatException;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtils {

    public static final String PHONE_REGEX = "(\\+7|7|8)\\d{10}";

    private static boolean isValidPhoneNumber(String phoneNumber){
        return phoneNumber.matches(PHONE_REGEX);
    }

    public static String convertToStandardFormat(String phoneNumber){
        Optional.of(phoneNumber)
                .filter(PhoneUtils::isValidPhoneNumber)
                .orElseThrow(() -> new PhoneFormatException("Phone must start with either +7, 7 or 8 and contain 11 digits"));

        final Matcher matcher = Pattern.compile(PHONE_REGEX).matcher(phoneNumber);
        matcher.find();
        int prefixLength = matcher.group(1).length();
        return "+7" + phoneNumber.substring(prefixLength);
    }
}
