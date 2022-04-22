package by.tms.dzen.yandexdzenrestc51.service;

import by.tms.dzen.yandexdzenrestc51.entity.DisLike;
import by.tms.dzen.yandexdzenrestc51.exception.ExistsException;
import by.tms.dzen.yandexdzenrestc51.repository.DisLikeRepository;
import by.tms.dzen.yandexdzenrestc51.repository.LikeRepository;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DisLikeService {
    private final DisLikeRepository disLikeRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final LikeService likeService;

    public DisLikeService(DisLikeRepository disLikeRepository, UserRepository userRepository, LikeRepository likeRepository, LikeService likeService) {
        this.disLikeRepository = disLikeRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.likeService = likeService;
    }


    public DisLike addDisLike(long userId, long postId) {
        if (disLikeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            throw new ExistsException();
        }

        if (likeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            likeService.removeLike(userId, postId);
        }

        return disLikeRepository.save(new DisLike(0, userRepository.findById(userId).get(), postId));
    }

    public void removeDisLike(long userId, long postId) {
        disLikeRepository.findByUserIdAndPostId(userId, postId).ifPresent(disLikeRepository::delete);
    }
}
