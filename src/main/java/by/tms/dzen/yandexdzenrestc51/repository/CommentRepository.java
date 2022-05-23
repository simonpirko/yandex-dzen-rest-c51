package by.tms.dzen.yandexdzenrestc51.repository;

import by.tms.dzen.yandexdzenrestc51.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findAllByPostId(long postId);
}
