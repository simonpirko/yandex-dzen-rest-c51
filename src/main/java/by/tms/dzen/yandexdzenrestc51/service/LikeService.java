package by.tms.dzen.yandexdzenrestc51.service;

import by.tms.dzen.yandexdzenrestc51.entity.Like;
import by.tms.dzen.yandexdzenrestc51.exception.ExistsException;
import by.tms.dzen.yandexdzenrestc51.repository.LikeRepository;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public LikeService(UserRepository userRepository, LikeRepository likeRepository) {
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    public Like addLike(long userId, long postId) {
        if (likeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            throw new ExistsException("Like already exists");
        }
        return likeRepository.save(new Like(0, userRepository.findById(userId).get(), postId));
    }

    public void removeLike(long userId, long postId) {
        likeRepository.findByUserIdAndPostId(userId, postId).ifPresent(likeRepository::delete);
    }
}
