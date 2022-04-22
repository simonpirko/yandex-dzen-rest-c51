package by.tms.dzen.yandexdzenrestc51.service;

import by.tms.dzen.yandexdzenrestc51.entity.Like;
import by.tms.dzen.yandexdzenrestc51.exception.ExistsException;
import by.tms.dzen.yandexdzenrestc51.repository.DisLikeRepository;
import by.tms.dzen.yandexdzenrestc51.repository.LikeRepository;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private DisLikeRepository disLikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DisLikeService disLikeService;

    public Like addLike(long userId, long postId) {
        if (likeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            throw new ExistsException();
        }

        if (disLikeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            disLikeService.removeDisLike(userId, postId);
        }

        return likeRepository.save(new Like(0, userRepository.findById(userId).get(), postId));
    }

    public void removeLike(long userId, long postId) {
        likeRepository.findByUserIdAndPostId(userId, postId).ifPresent(likeRepository::delete);
    }
}
