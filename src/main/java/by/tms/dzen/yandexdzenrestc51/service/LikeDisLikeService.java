package by.tms.dzen.yandexdzenrestc51.service;

import by.tms.dzen.yandexdzenrestc51.entity.DisLike;
import by.tms.dzen.yandexdzenrestc51.entity.Like;
import by.tms.dzen.yandexdzenrestc51.exception.ExistsException;
import by.tms.dzen.yandexdzenrestc51.repository.DisLikeRepository;
import by.tms.dzen.yandexdzenrestc51.repository.LikeRepository;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LikeDisLikeService {
    private final DisLikeRepository disLikeRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public LikeDisLikeService(UserRepository userRepository, LikeRepository likeRepository, DisLikeRepository disLikeRepository) {
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.disLikeRepository = disLikeRepository;
    }

    public Like addLike(long userId, long postId) {
        if (likeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            throw new ExistsException();
        }

        if (disLikeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            removeDisLike(userId, postId);
        }

        return likeRepository.save(new Like(0, userRepository.findById(userId).get(), postId));
    }

    public void removeLike(long userId, long postId) {
        likeRepository.findByUserIdAndPostId(userId, postId).ifPresent(likeRepository::delete);
    }

    public DisLike addDisLike(long userId, long postId) {
        if (disLikeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            throw new ExistsException();
        }

        if (likeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            removeLike(userId, postId);
        }

        return disLikeRepository.save(new DisLike(0, userRepository.findById(userId).get(), postId));
    }

    public void removeDisLike(long userId, long postId) {
        disLikeRepository.findByUserIdAndPostId(userId, postId).ifPresent(disLikeRepository::delete);
    }
}
