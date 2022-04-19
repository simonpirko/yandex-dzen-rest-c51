package by.tms.dzen.yandexdzenrestc51.mapper;

import by.tms.dzen.yandexdzenrestc51.dto.UserDTO;
import by.tms.dzen.yandexdzenrestc51.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private static BCryptPasswordEncoder passwordEncoder;

    public UserConverter(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static User convertToUserFromUserSignupDTO(UserDTO userDTO) {
        return User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .build();
    }
}
