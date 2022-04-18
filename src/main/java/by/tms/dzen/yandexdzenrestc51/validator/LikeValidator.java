package by.tms.dzen.yandexdzenrestc51.validator;

import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.exception.NotFoundException;
import by.tms.dzen.yandexdzenrestc51.repository.PostRepository;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:messages.properties")
public class LikeValidator {
    @Value("${userOrPostNotFound}")
    private String msgUserOrPostNotFound;

    @Value("${invalidUserIdOrPostId}")
    private String msgInvalidUserIdOrPostId;

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public LikeValidator(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public void validateID (long userId, long postId) {
        if(userId < 1 | postId < 1) {
            throw new InvalidException(msgInvalidUserIdOrPostId);
       }
    }

    public boolean existsByUserIdAndPostId(long userId, long postId) {
        if (userRepository.findById(userId).isEmpty() || postRepository.findById(postId).isEmpty()) {
            throw new NotFoundException(msgUserOrPostNotFound);
        }
        return true;
    }
}
