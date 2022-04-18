package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.entity.DisLike;
import by.tms.dzen.yandexdzenrestc51.exception.NotFoundException;
import by.tms.dzen.yandexdzenrestc51.service.LikeDisLikeService;
import by.tms.dzen.yandexdzenrestc51.validator.LikeValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dislike")
public class DisLikeController {
    private final LikeDisLikeService likeDisLikeService;
    private final LikeValidator likeValidator;

    public DisLikeController(LikeValidator likeValidator, LikeDisLikeService likeDisLikeService) {
        this.likeValidator = likeValidator;
        this.likeDisLikeService = likeDisLikeService;
    }

    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<DisLike> save(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId) {
        likeValidator.validateID(userId, postId);
        likeValidator.existsByUserIdAndPostId(userId, postId);

        return ResponseEntity.ok(likeDisLikeService.addDisLike(userId, postId));
    }

    @DeleteMapping("/{userId}/{postId}")
    public void delete(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId) {
        likeValidator.validateID(userId, postId);

        if (likeValidator.existsByUserIdAndPostId(userId, postId)) {
            likeDisLikeService.removeDisLike(userId, postId);
        } else {
            throw new NotFoundException("Like not found");
        }
    }
}
