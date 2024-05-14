package ru.charushnikov.microauthservice.exception;

public class IncorrectPasswordOrLoginException extends RuntimeException {
    public IncorrectPasswordOrLoginException(String message) {
        super(message);
    }
}
