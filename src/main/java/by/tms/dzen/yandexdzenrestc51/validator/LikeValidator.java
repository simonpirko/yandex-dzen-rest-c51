package by.tms.dzen.yandexdzenrestc51.validator;

import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.exception.NotFoundException;
import by.tms.dzen.yandexdzenrestc51.repository.PostRepository;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class LikeValidator {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public LikeValidator(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public void validateID (long userId, long postId) {
        if(userId < 1 | postId < 1) {
            throw new InvalidException("Invalid userId or postId");
       }
    }

    public boolean existsByUserIdAndPostId(long userId, long postId) {
        if (userRepository.findById(userId).isEmpty() || postRepository.findById(postId).isEmpty()) {
            throw new NotFoundException("User or post not found");
        }
        return true;
    }
}
