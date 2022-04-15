package by.tms.dzen.yandexdzenrestc51.repository;

import by.tms.dzen.yandexdzenrestc51.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
