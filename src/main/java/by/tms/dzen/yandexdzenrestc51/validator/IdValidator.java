package by.tms.dzen.yandexdzenrestc51.validator;

import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.repository.PostRepository;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class IdValidator {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public IdValidator(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public void validateID(long id) {
        if (id < 1) {
            throw new InvalidException();
        }
    }

    public void validatePostID(long id) {
        if (id < 1 | postRepository.findById(id).isEmpty()) {
            throw new InvalidException();
        }
    }

    public void validateUserID(long id) {
        if (id < 1 | userRepository.findById(id).isEmpty()) {
            throw new InvalidException();
        }
    }
}
