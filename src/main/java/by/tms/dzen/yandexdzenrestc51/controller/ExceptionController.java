package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.exception.NotFoundException;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    private final Environment environment;

    public ExceptionController(Environment environment) {
        this.environment = environment;
    }

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<Object> invalidInputException(InvalidException ex){
        return new ResponseEntity(environment.getProperty("IvalidInput"), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> userNotFoundException(NotFoundException ex){
        return new ResponseEntity(environment.getProperty("NotFound"),HttpStatus.BAD_REQUEST);
    }
}
