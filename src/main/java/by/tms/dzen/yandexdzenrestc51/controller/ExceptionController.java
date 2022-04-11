package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @Autowired
    private Environment environment;

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<Object> invalidInputException(InvalidException ex){
        return new ResponseEntity(environment.getProperty("IvalidInput"), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundException(UserNotFoundException ex){
        return new ResponseEntity(environment.getProperty("UserNotFound"),HttpStatus.BAD_REQUEST);
    }
}
