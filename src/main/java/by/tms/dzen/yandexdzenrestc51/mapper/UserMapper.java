package by.tms.dzen.yandexdzenrestc51.mapper;

import by.tms.dzen.yandexdzenrestc51.dto.UserDTO;
import by.tms.dzen.yandexdzenrestc51.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDto(User user);
    User userDtoToUser(UserDTO userDto);
}
