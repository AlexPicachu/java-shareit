package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;


/**
 * контракт для реализации UserStorageImpl
 */
public interface UserStorage {

    /**
     * @return Метод возвращает всех пользователей
     */
    Collection<User> getAllUsers();

    /**
     * Метод для создания пользователя
     *
     * @param user - переданный пользователь
     * @return - нового пользователя
     */
    User createUser(User user);

    /**
     * Метод обновления пользователя
     *
     * @param user - переданные параметры пользователя для обновления существующего
     * @return - обновленного пользователя
     */
    User updateUser(User user);

    /**
     * Метод запроса пользователя по ID
     *
     * @param userId - пользователя
     * @return - пользователя по запросу
     */
    User getUserById(long userId);

    /**
     * Метод удаления пользователя по ID
     *
     * @param userId - удаляемого пользователя
     */
    void deleteUser(long userId);
}
