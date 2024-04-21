package ru.charushnikov.microauthservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.charushnikov.microauthservice.exception.DuplicatePassportException;
import ru.charushnikov.microauthservice.exception.DuplicatedMailException;
import ru.charushnikov.microauthservice.exception.DuplicatedPhoneException;
import ru.charushnikov.microauthservice.exception.PhoneFormatException;
import ru.charushnikov.microauthservice.model.dto.response.ErrorMessage;

import java.time.format.DateTimeParseException;
import java.util.Objects;

@Slf4j
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
        return new  ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(DuplicatedPhoneException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleDuplicateMails(DuplicatedPhoneException exception) {
        return new  ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(DuplicatePassportException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleDuplicateMails(DuplicatePassportException exception) {
        return new  ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler(PhoneFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleDuplicateMails(PhoneFormatException exception) {
        return new  ErrorMessage(exception.getMessage());
    }
}
