package by.tms.dzen.yandexdzenrestc51.repository;

import by.tms.dzen.yandexdzenrestc51.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}
