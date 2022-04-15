package by.tms.dzen.yandexdzenrestc51.mapper;

import by.tms.dzen.yandexdzenrestc51.dto.UserDto;
import by.tms.dzen.yandexdzenrestc51.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
}
