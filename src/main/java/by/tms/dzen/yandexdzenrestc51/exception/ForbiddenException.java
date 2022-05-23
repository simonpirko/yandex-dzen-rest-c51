package by.tms.dzen.yandexdzenrestc51.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String msg) {
        super(msg);
    }

    public ForbiddenException(String msg, Throwable t) {
        super(msg, t);
    }

    public ForbiddenException(Throwable t) {
        super(t);
    }

    public ForbiddenException() {
        super();
    }
}