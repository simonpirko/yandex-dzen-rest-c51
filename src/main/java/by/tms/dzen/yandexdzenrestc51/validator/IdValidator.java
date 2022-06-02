package by.tms.dzen.yandexdzenrestc51.validator;

import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.repository.*;
import org.springframework.stereotype.Component;

@Component
public class IdValidator {
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public IdValidator(UserRepository userRepository,
                       PostRepository postRepository,
                       CommentRepository commentRepository,
                       CategoryRepository categoryRepository,
                       TagRepository tagRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    public void validateId(long id) {
        if (id < 1) {
            throw new InvalidException();
        }
    }

    public void validatePostId(long id) {
        if (id < 1 | postRepository.findById(id).isEmpty()) {
            throw new InvalidException();
        }
    }

    public void validateUserId(long id) {
        if (id < 1 | userRepository.findById(id).isEmpty()) {
            throw new InvalidException();
        }
    }

    public void validateCommentId(long id) {
        if (id < 0 | commentRepository.findById(id).isEmpty()) {
            throw new InvalidException();
        }
    }

    public void validateCategoryId(long id) {
        if (id < 1 | categoryRepository.findById(id).isEmpty()) {
            throw new InvalidException();
        }
    }

    public void validateTagId(long id) {
        if (tagRepository.findById(id).isEmpty()) {
            throw new InvalidException();
        }
    }
}
