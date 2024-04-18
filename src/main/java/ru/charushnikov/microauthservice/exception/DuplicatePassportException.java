package ru.charushnikov.microauthservice.exception;

public class DuplicatePassportException extends RuntimeException {
    public DuplicatePassportException(String message) {
        super(message);
    }
}
