package ru.charushnikov.microauthservice.exception;

public class DuplicatedMailException extends RuntimeException {
    public DuplicatedMailException(String message) {
        super(message);
    }
}
