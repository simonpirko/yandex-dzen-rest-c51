package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.entity.Like;
import by.tms.dzen.yandexdzenrestc51.exception.ExistsException;
import by.tms.dzen.yandexdzenrestc51.exception.InvalidException;
import by.tms.dzen.yandexdzenrestc51.exception.NotFoundException;
import by.tms.dzen.yandexdzenrestc51.repository.LikeRepository;
import by.tms.dzen.yandexdzenrestc51.repository.PostRepository;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/like")
public class LikeController {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public LikeController(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<Like> like(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId) {
        validateID(userId, postId);

        if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new ExistsException("Like already exists");
        }

        Like save = likeRepository.save(new Like(0, userRepository.findById(userId).get(), postId));

        return ResponseEntity.ok(save);
    }

    @DeleteMapping("/{userId}/{postId}")
    public void deleteLike(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId) {
        validateID(userId, postId);

        if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
            likeRepository.findByUserIdAndPostId(userId, postId).ifPresent(likeRepository::delete);
        } else {
            throw new NotFoundException("Like not found");
        }
    }

    private void validateID (long userId, long postId) {
        if(userId < 1 | postId < 1) {
            throw new InvalidException("Invalid userId or postId");
        }

        if (userRepository.findById(userId).isEmpty() | postRepository.findById(postId).isEmpty()) {
            throw new NotFoundException("User or post not found");
        }
    }
}
