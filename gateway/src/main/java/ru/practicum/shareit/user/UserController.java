package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;


import javax.validation.Valid;


/**
 * класс валидации входных параметров
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserClient userClient;

    /**
     * Метод валидации входящих параметров, перед просмотром всех пользователей
     */
    @GetMapping
    public ResponseEntity<Object> getAllUser() {
        log.info("Вернули список всех пользователей");
        return userClient.getAllUser();
    }

    /**
     * Метод валидации входящих параметров, перед добавлением нового пользователя
     */
    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Создали нового пользователя {}", userDto);
        return userClient.createUser(userDto);
    }

    /**
     * Метод валидации входящих параметров, перед обновлением пользователя
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUserById(@PathVariable long id, @RequestBody UserDto userDto) {
        log.info("обновили данные пользователя с id {}", id);
        return userClient.updateUserById(id, userDto);
    }

    /**
     * Метод валидации входящих параметров, перед удалением пользователя
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable long id) {
        log.info("удален пользователь с id {}", id);
        return userClient.deleteUserById(id);
    }

    /**
     * Метод валидации входящих параметров, перед получением пользователя по id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable long id) {
        log.info("вернули пользователя с id {}", id);
        return userClient.getUserById(id);
    }

}
