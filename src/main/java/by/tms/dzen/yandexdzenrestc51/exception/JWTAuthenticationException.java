package by.tms.dzen.yandexdzenrestc51.exception;

import javax.naming.AuthenticationException;

public class JWTAuthenticationException extends AuthenticationException {

    public JWTAuthenticationException(String msg) {
        super(msg);
    }
}
