package by.tms.dzen.yandexdzenrestc51.service;

import by.tms.dzen.yandexdzenrestc51.entity.Like;
import by.tms.dzen.yandexdzenrestc51.exception.ExistsException;
import by.tms.dzen.yandexdzenrestc51.repository.DisLikeRepository;
import by.tms.dzen.yandexdzenrestc51.repository.LikeRepository;
import by.tms.dzen.yandexdzenrestc51.repository.PostRepository;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final DisLikeRepository disLikeRepository;
    private final UserRepository userRepository;
    private final DisLikeService disLikeService;
    private final PostRepository postRepository;

    public LikeService(LikeRepository likeRepository, DisLikeRepository disLikeRepository, UserRepository userRepository, @Lazy DisLikeService disLikeService, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.disLikeRepository = disLikeRepository;
        this.userRepository = userRepository;
        this.disLikeService = disLikeService;
        this.postRepository = postRepository;
    }

    public Like addLike(long userId, long postId) {
        if (likeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            throw new ExistsException();
        }

        if (disLikeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            disLikeService.removeDisLike(userId, postId);
        }

        return likeRepository.save(new Like(0, userRepository.findById(userId).get(), postRepository.getById(postId)));
    }

    public void removeLike(long userId, long postId) {
        likeRepository.findByUserIdAndPostId(userId, postId).ifPresent(likeRepository::delete);
    }
}
