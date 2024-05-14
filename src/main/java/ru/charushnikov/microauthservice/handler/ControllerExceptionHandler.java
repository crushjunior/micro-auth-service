package ru.charushnikov.microauthservice.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.charushnikov.microauthservice.exception.*;
import ru.charushnikov.microauthservice.model.dto.response.ErrorMessage;

import java.time.format.DateTimeParseException;
import java.util.Objects;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        return new ErrorMessage(Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMethodJsonNotValid(DateTimeParseException exception) {
        String errorMessage = messageSource.getMessage("validation.invalidDate.message",
                null, LocaleContextHolder.getLocale());
        return new ErrorMessage(errorMessage);
    }

    @ExceptionHandler(DuplicatedMailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleDuplicateMails(DuplicatedMailException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(DuplicatedPhoneException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleDuplicatePhone(DuplicatedPhoneException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(DuplicatePassportException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleDuplicatePassport(DuplicatePassportException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(PhoneFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handlePhoneFormat(PhoneFormatException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleResourceNotFound(ResourceNotFoundException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(IncorrectPasswordOrLoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleIncorrectPasswordOrLogin(IncorrectPasswordOrLoginException exception) {
        return new ErrorMessage(exception.getMessage());
    }
}
