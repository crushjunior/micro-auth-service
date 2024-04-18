package ru.charushnikov.microauthservice.exception;

public class DuplicatedPhoneException extends RuntimeException{

    public DuplicatedPhoneException(String message) {
        super(message);
    }
}
