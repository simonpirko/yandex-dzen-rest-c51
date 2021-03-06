package by.tms.dzen.yandexdzenrestc51.repository;

import by.tms.dzen.yandexdzenrestc51.entity.DisLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisLikeRepository extends JpaRepository<DisLike, Long> {
    Optional<DisLike> findByUserIdAndPostId(long userId, long postId);
}
