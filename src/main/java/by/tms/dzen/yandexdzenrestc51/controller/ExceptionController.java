package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.exception.ExistsException;
import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@PropertySource("classpath:messages.properties")
public class ExceptionController extends ResponseEntityExceptionHandler {

    @Value("${invalidInput}")
    private String msgInvalidInput;

    @Value("${notFound}")
    private String msgNotFound;

    @Value("${exists}")
    private String msgExists;

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<Object> invalidInputException(InvalidException ex) {
        return new ResponseEntity(msgInvalidInput, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> userNotFoundException(NotFoundException ex) {
        return new ResponseEntity(msgNotFound, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistsException.class)
    public ResponseEntity<Object> userExistsException(ExistsException ex) {
        return new ResponseEntity(msgExists, HttpStatus.CONFLICT);
    }
}
