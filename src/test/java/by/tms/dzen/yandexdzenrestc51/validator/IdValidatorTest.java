package by.tms.dzen.yandexdzenrestc51.validator;

import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Profile("dev")
class IdValidatorTest {
    @Autowired
    private IdValidator idValidator;

    @Test
    void validateIdZeroFalse() {
        assertThrows(InvalidException.class, () -> idValidator.validateId(0));
    }

    @Test
    void validateIdMinusFalse() {
        assertThrows(InvalidException.class, () -> idValidator.validateId(-1L));
    }

    @Test
    void validateIdTrue() {
        assertDoesNotThrow(() -> idValidator.validateId(1L));
    }

    @Test
    void validatePostId() {
        assertDoesNotThrow(() -> idValidator.validatePostId(1L));
    }

    @Test
    void validateUserIdTrue() {
        assertDoesNotThrow(() -> idValidator.validateUserId(1L));
    }

    @Test
    void validateUserIdZeroFalse() {
        assertThrows(InvalidException.class, () -> idValidator.validateUserId(0));
    }

    @Test
    void validateUserIdMinusFalse() {
        assertThrows(InvalidException.class, () -> idValidator.validateUserId(-1));
    }

    @Test
    void validateCommentIdTrue() {
        assertDoesNotThrow(() -> idValidator.validateCommentId(1L));
    }

    @Test
    void validateCommentIdZeroFalse() {
        assertThrows(InvalidException.class, () -> idValidator.validateCommentId(0));
    }

    @Test
    void validateCommentIdMinusFalse() {
        assertThrows(InvalidException.class, () -> idValidator.validateCommentId(-1L));
    }

    @Test
    void validateCategoryIdTrue() {
        assertDoesNotThrow(() -> idValidator.validateCategoryId(1L));
    }

    @Test
    void validateCategoryIdZeroFalse() {
        assertThrows(InvalidException.class, () -> idValidator.validateCategoryId(0));
    }

    @Test
    void validateCategoryIdMinusFalse() {
        assertThrows(InvalidException.class, () -> idValidator.validateCategoryId(-1L));
    }

    @Test
    void validateTagIdTrue() {
        assertDoesNotThrow(() -> idValidator.validateTagId(1L));
    }

    @Test
    void validateTagIdZeroFalse() {
        assertThrows(InvalidException.class, () -> idValidator.validateTagId(0));
    }

    @Test
    void validateTagIdMinusFalse() {
        assertThrows(InvalidException.class, () -> idValidator.validateTagId(-1L));
    }
}