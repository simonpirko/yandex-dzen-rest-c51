package by.tms.dzen.yandexdzenrestc51.controller;

import by.tms.dzen.yandexdzenrestc51.entity.DisLike;
import by.tms.dzen.yandexdzenrestc51.repository.DisLikeRepository;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import by.tms.dzen.yandexdzenrestc51.service.LikeService;
import by.tms.dzen.yandexdzenrestc51.validator.LikeValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dislike")
public class DisLikeController {
    private final LikeService likeService;
    private final DisLikeRepository disLikeRepository;
    private final UserRepository userRepository;
    private final LikeValidator likeValidator;

    public DisLikeController(LikeService likeService, DisLikeRepository disLikeRepository, UserRepository userRepository, LikeValidator likeValidator) {
        this.likeService = likeService;
        this.disLikeRepository = disLikeRepository;
        this.userRepository = userRepository;
        this.likeValidator = likeValidator;
    }

    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<DisLike> dislike(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId) {
        likeValidator.validateID(userId, postId);
        likeValidator.existsByUserIdAndPostId(userId, postId);

        DisLike save = disLikeRepository.save(new DisLike(0, userRepository.findById(userId).get(), postId));

        return ResponseEntity.ok(save);
    }
}
