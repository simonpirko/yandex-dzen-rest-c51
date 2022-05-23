package by.tms.dzen.yandexdzenrestc51.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class JWTAuthenticationException extends AuthenticationException {

    public JWTAuthenticationException(String msg) {
        super(msg);
    }
}
