package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.entity.Like;
import by.tms.dzen.yandexdzenrestc51.exception.NotFoundException;
import by.tms.dzen.yandexdzenrestc51.service.LikeService;
import by.tms.dzen.yandexdzenrestc51.validator.LikeValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/like")
public class LikeController {
    private final LikeService likeService;
    private final LikeValidator likeValidator;

    public LikeController(LikeService likeService, LikeValidator likeValidator) {
        this.likeService = likeService;
        this.likeValidator = likeValidator;
    }

    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<Like> like(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId) {
        likeValidator.validateID(userId, postId);
        likeValidator.existsByUserIdAndPostId(userId, postId);

        return ResponseEntity.ok(likeService.addLike(userId, postId));
    }

    @DeleteMapping("/{userId}/{postId}")
    public void deleteLike(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId) {
        likeValidator.validateID(userId, postId);

        if (likeValidator.existsByUserIdAndPostId(userId, postId)) {
            likeService.removeLike(userId, postId);
        } else {
            throw new NotFoundException("Like not found");
        }
    }
}
