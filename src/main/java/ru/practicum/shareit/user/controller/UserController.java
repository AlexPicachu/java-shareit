package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;


import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;


/**
 * Класс контроллер, для принятия входящих запросов к сущности User
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Метод возвращающий коллекцию пользователей преобразованных в DTO
     */
    @GetMapping
    public Collection<UserDto> getAllUser() {
        return userService.getAllUsers().stream()
                .map(UserMapper::userToDto)
                .collect(Collectors.toList());
    }

    /**
     * Метод создает нового пользователя и возвращает преобразованного в DTO
     */
    @PostMapping
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        User user = userService.createUser(UserMapper.dtoToUser(userDto));
        return UserMapper.userToDto(user);
    }

    /**
     * метод обновляет данные пользователя и возвращает преобразованного в DTO
     */
    @PatchMapping("/{id}")
    public UserDto updateUserById(@PathVariable long id, @RequestBody UserDto userDto) {
        User user = userService.updateUser(UserMapper.userDtoToUserByid(id, userDto));
        return UserMapper.userToDto(user);
    }

    /**
     * Метод удаляющий пользователя по id
     */
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id) {
        userService.deleteUser(id);
    }

    /**
     * Метод возвращающий пользователя по id
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return UserMapper.userToDto(user);
    }

}
