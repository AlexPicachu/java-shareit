package ru.practicum.shareit.user.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exeption.NoDataRequestedInStorageException;
import ru.practicum.shareit.user.model.User;

import java.util.*;

/**
 * Класс реализовывающий interface UserStorage
 */
@Slf4j
@Component
public class UserStorageImpl implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long userId = 0;

    /**
     * @return Метод возвращает всех пользователей
     */
    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    /**
     * Метод для создания пользователя
     *
     * @param user - переданный пользователь
     * @return - нового пользователя
     */
    @Override
    public User createUser(User user) {
        for (User value : users.values()) {
            if (value.getEmail().equals(user.getEmail())) {
                log.info("Пользователь с почтой = " + user.getEmail() + " уже существует");
                throw new NoDataRequestedInStorageException("Пользователь с почтой = " + user.getEmail() + " уже существует");
            }
        }
        user.setId(++userId);
        users.put(user.getId(), user);
        log.info("Добавлен новый пользователь - {}", users.get(user.getId()));
        return users.get(user.getId());
    }

    /**
     * Метод обновления пользователя
     *
     * @param user - переданные параметры пользователя для обновления существующего
     * @return - обновленного  пользователя
     */
    @Override
    public User updateUser(User user) {
        checkUser(user.getId());
        if (user.getEmail() != null) {
            for (User value : users.values()) {
                if (Objects.equals(value.getEmail(), user.getEmail()) && user.getId() != value.getId()) {
                    throw new NoDataRequestedInStorageException("Уже существует пользователь с почтой = " + user.getEmail());
                }
            }
            users.get(user.getId()).setEmail(user.getEmail());
        }
        if (user.getName() != null) {
            users.get(user.getId()).setName(user.getName());
        }
        log.info("Обновлены данные пользователя с id = " + user.getId());
        return users.get(user.getId());
    }

    /**
     * Метод запроса пользователя по ID
     *
     * @param id - пользователя
     * @return - пользователя по запросу
     */
    @Override
    public User getUserById(long id) {
        checkUser(id);
        log.info("По заданному id найден пользователь = {}", users.get(id));
        return users.get(id);
    }

    /**
     * Метод удаления пользователя по ID
     *
     * @param id - удаляемого пользователя
     */
    @Override
    public void deleteUser(long id) {
        checkUser(id);
        users.remove(id);
        log.info("Удален пользователь с id = " + id);
    }

    /**
     * Метод для проверки пользователя, на наличие его в базе данных
     */
    private void checkUser(long checkId) {
        if (!users.containsKey(checkId)) {
            log.info("Пользователя с таким id = " + checkId + " не существует");
            throw new NoDataRequestedInStorageException("Пользователя с таким id = " + checkId + " не существует");
        }
    }

}
