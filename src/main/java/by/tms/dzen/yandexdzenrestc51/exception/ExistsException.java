package by.tms.dzen.yandexdzenrestc51.exception;

public class ExistsException extends RuntimeException {
    public ExistsException(String message) {
        super(message);
    }

    public ExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistsException(Throwable cause) {
        super(cause);
    }

    public ExistsException() {
    }

}
