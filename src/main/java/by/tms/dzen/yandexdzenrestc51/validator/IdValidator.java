package by.tms.dzen.yandexdzenrestc51.validator;

import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import org.springframework.stereotype.Component;

@Component
public class IdValidator {
    public void validateID(long id) {
        if (id < 1) {
            throw new InvalidException();
        }
    }
}
