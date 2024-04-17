package ru.charushnikov.microauthservice.exception;

public class PhoneFormatException extends RuntimeException {
    public PhoneFormatException(String message) {
        super(message);
    }
}
