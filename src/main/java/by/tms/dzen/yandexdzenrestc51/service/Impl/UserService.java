package by.tms.dzen.yandexdzenrestc51.service.Impl;

import by.tms.dzen.yandexdzenrestc51.dto.UserDTO;
import by.tms.dzen.yandexdzenrestc51.entity.Role;
import by.tms.dzen.yandexdzenrestc51.entity.Status;
import by.tms.dzen.yandexdzenrestc51.entity.User;
import by.tms.dzen.yandexdzenrestc51.exception.ExistsException;
import by.tms.dzen.yandexdzenrestc51.mapper.UserConverter;
import by.tms.dzen.yandexdzenrestc51.repository.RoleRepository;
import by.tms.dzen.yandexdzenrestc51.repository.UserRepository;
import by.tms.dzen.yandexdzenrestc51.service.Crud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService implements Crud<User> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void registration(UserDTO userDTO) {
        userRepository.findByUsername(userDTO.getUsername()).ifPresent(user -> {
            log.error("IN registration - user with username: {} already exists", userDTO.getUsername());
            throw new ExistsException("User with username: " + userDTO.getUsername() + " already exists");
        });

        userRepository.findByEmail(userDTO.getEmail()).ifPresent(user -> {
            log.error("IN registration - user with email: {} already exists", userDTO.getEmail());
            throw new ExistsException("User with email: " + userDTO.getEmail() + " already exists");
        });

        User user = UserConverter.convertToUserFromUserSignupDTO(userDTO);
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setTypeOfRole("USER");
        roles.add(role);
        user.setRoleList(roles);
        user.setStatus(Status.ACTIVE);
        role.setUser(user);
        userRepository.save(user);
        roleRepository.save(role);

        log.info("User named {} registered", userDTO.getUsername());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username: " + username + " not found"));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        user.setStatus(Status.DELETED);
        User deleted = userRepository.save(user);

        log.info("IN deleteUser - user: {} successfully deleted", deleted);
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.getById(id);
        user.setStatus(Status.DELETED);
        User deleted = userRepository.save(user);

        log.info("IN deleteUser - user: {} successfully deleted", deleted);
    }

    @Override
    public void update(User user) {
        User updated = userRepository.save(user);

        log.info("IN updateUser - user: {} successfully updated", updated);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id: " + id + " not found"));
    }
}
