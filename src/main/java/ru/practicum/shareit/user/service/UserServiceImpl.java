package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;


/**
 * Класс реализовывающий interface UserService
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    /**
     * @return Метод возвращает всех пользователей
     */
    @Override
    public Collection<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    /**
     * Метод для создания пользователя
     *
     * @param user - переданный пользователь
     * @return - нового пользователя
     */
    @Override
    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    /**
     * Метод обновления пользователя
     *
     * @param user - переданные параметры пользователя для обновления существующего
     * @return - обновленного пользователя
     */
    @Override
    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    /**
     * Метод запроса пользователя по ID
     *
     * @param userId - пользователя
     * @return - пользователя по запросу
     */
    @Override
    public User getUserById(long userId) {
        return userStorage.getUserById(userId);
    }

    /**
     * Метод удаления пользователя по ID
     *
     * @param userId - удаляемого пользователя
     */
    @Override
    public void deleteUser(long userId) {
        userStorage.deleteUser(userId);
    }
}
