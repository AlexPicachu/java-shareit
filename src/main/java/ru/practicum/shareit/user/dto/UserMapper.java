package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

/**
 * Класс Mapper преобразовывает User в UserDto и обратно
 */
@Component
@AllArgsConstructor
public class UserMapper {

    /**
     * Метод преобразовывает User в UserDto
     *
     * @param user - переданный пользователь
     */
    public static UserDto userToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    /**
     * Метод преобразовывает UserDto в User
     *
     * @param userDto переданный пользователь в в формате DTO
     * @return пользователя
     */
    public static User dtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .build();
    }

    /**
     * ММетод преобразовывает UserDto в User
     *
     * @param id      - пользователя
     * @param userDto - переданный пользователь в в формате DTO
     * @return - пользователя
     */
    public static User userToDtoDyId(long id, UserDto userDto) {
        return User.builder()
                .id(id)
                .email(userDto.getEmail())
                .name(userDto.getName())
                .build();
    }
}
