package by.tms.dzen.yandexdzenrestc51.repository;

import by.tms.dzen.yandexdzenrestc51.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
