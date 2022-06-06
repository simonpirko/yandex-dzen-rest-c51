package by.tms.dzen.yandexdzenrestc51.service.Impl;

import by.tms.dzen.yandexdzenrestc51.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void registration() {
    }

    @Test
    void findByUsername() {
        User user = userService.findByUsername("user");
        assertNotNull(user);
        assertTrue(user.getId() > 0);
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void testDelete() {
    }

    @Test
    void update() {
    }

    @Test
    void findById() {
        User user = userService.findByUsername("user");
        assertNotNull(user);
        assertTrue(user.getId() > 0);
    }

    @Test
    void getAuthenticationUserName() {
    }

    @Test
    void getAuthenticationUser() {
    }

    @Test
    void addSubscriberUser() {
    }

    @Test
    void deleteSubscriberUser() {
    }
}