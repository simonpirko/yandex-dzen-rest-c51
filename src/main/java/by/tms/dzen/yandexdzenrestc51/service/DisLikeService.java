package by.tms.dzen.yandexdzenrestc51.service;

import by.tms.dzen.yandexdzenrestc51.entity.DisLike;
import by.tms.dzen.yandexdzenrestc51.exception.ExistsException;
import by.tms.dzen.yandexdzenrestc51.repository.DisLikeRepository;
import by.tms.dzen.yandexdzenrestc51.repository.LikeRepository;
import by.tms.dzen.yandexdzenrestc51.repository.PostRepository;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DisLikeService {
    private final DisLikeRepository disLikeRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final LikeService likeService;
    private final PostRepository postRepository;

    public DisLikeService(DisLikeRepository disLikeRepository, UserRepository userRepository, LikeRepository likeRepository, LikeService likeService, PostRepository postRepository) {
        this.disLikeRepository = disLikeRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.likeService = likeService;
        this.postRepository = postRepository;
    }

    public DisLike addDisLike(long userId, long postId) {
        if (disLikeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            throw new ExistsException();
        }

        if (likeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            likeService.removeLike(userId, postId);
        }

        return disLikeRepository.save(new DisLike(0, userRepository.findById(userId).get(), postRepository.getById(postId)));
    }

    public void removeDisLike(long userId, long postId) {
        disLikeRepository.findByUserIdAndPostId(userId, postId).ifPresent(disLikeRepository::delete);

        log.info("IN removeDisLike - dislike with userId: {} and postId: {} successfully deleted", userId, postId);
    }
}
