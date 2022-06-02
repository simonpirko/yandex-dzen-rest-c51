package by.tms.dzen.yandexdzenrestc51.validator;

import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class IdValidatorTest {
    @Autowired
    private IdValidator idValidator;

    @Test
    void validateIdFalseZero() {
        assertThrows(InvalidException.class, () -> idValidator.validateId(0));
    }

    @Test
    void validateIdFalseMinus() {
        assertThrows(InvalidException.class, () -> idValidator.validateId(-1));
    }

    @Test
    void validateIdTrue() {
        assertDoesNotThrow(() -> idValidator.validateId(1));
    }

    @Test
    void validatePostId() {
    }

    @Test
    void validateUserId() {
    }

    @Test
    void validateCommentId() {
    }

    @Test
    void validateCategoryId() {
    }

    @Test
    void validateTagId() {
    }
}