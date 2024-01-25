package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;


import java.util.Collection;


/**
 * Класс реализовывающий interface UserService
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * @return Метод возвращает всех пользователей
     */
    @Override
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Метод для создания пользователя
     *
     * @param user - переданный пользователь
     * @return - нового пользователя
     */
    @Override
    public User createUser(User user) {
        userRepository.save(user);
        log.info("Добавлен новый пользователь {} ", user);
        return user;
    }

    /**
     * Метод обновления пользователя
     *
     * @param user - переданные параметры пользователя для обновления существующего
     * @return - обновленного пользователя
     */
    @Override
    public User updateUser(User user) {
        // checkUser(user.getId());
        User oldUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("Пользователя с таким id = " + user.getId() + "  не существует"));
        if (user.getName() == null) {
            user.setName(oldUser.getName());
        }
        if (user.getEmail() == null) {
            user.setEmail(oldUser.getEmail());
        }
        userRepository.save(user);
        log.info("Обновлены данные пользователя {}", user);
        return user;
    }

    /**
     * Метод запроса пользователя по ID
     *
     * @param id - пользователя
     * @return - пользователя по запросу
     */
    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователя с таким id = " + id + "  не существует"));

    }

    /**
     * Метод удаления пользователя по ID
     *
     * @param id - удаляемого пользователя
     */
    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
        log.info("Удален пользователь с id = {}", id);
    }

    /**
     * Метод для проверки пользователя, на наличие его в базе данных
     *
     * @param checkId - проверяемого пользователя
     */
    private void checkUser(long checkId) {
        if (!userRepository.existsById(checkId)) {
            log.info("Пользователя с таким id = " + checkId + " не существует");
            throw new NotFoundException("Пользователя с таким id = " + checkId + " не существует");
        }

    }
}
