package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.entity.Like;
import by.tms.dzen.yandexdzenrestc51.exception.NotFoundException;
import by.tms.dzen.yandexdzenrestc51.service.LikeDisLikeService;
import by.tms.dzen.yandexdzenrestc51.validator.LikeValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/like")
public class LikeController {
    private final LikeDisLikeService likeDisLikeService;
    private final LikeValidator likeValidator;

    public LikeController(LikeDisLikeService likeDisLikeService, LikeValidator likeValidator) {
        this.likeDisLikeService = likeDisLikeService;
        this.likeValidator = likeValidator;
    }

    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<Like> save(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId) {
        likeValidator.validateID(userId, postId);
        likeValidator.existsByUserIdAndPostId(userId, postId);

        return ResponseEntity.ok(likeDisLikeService.addLike(userId, postId));
    }

    @DeleteMapping("/{userId}/{postId}")
    public void delete(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId) {
        likeValidator.validateID(userId, postId);

        if (likeValidator.existsByUserIdAndPostId(userId, postId)) {
            likeDisLikeService.removeLike(userId, postId);
        } else {
            throw new NotFoundException("Like not found");
        }
    }
}
