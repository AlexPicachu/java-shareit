package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;

/**
 * класс реализовывающий interface ItemService
 */
@Slf4j
@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    /**
     * Метод добавляет вещь
     */
    @Override
    public Item addItem(long userId, ItemDto itemDto) {
        checkUser(userId);
        User user = userStorage.getUserById(userId);
        Item item = ItemMapper.toItem(user, itemDto);
        return itemStorage.create(item);
    }

    /**
     * Метод обновления вещи
     */
    @Override
    public Item updateItem(long id, long userId, ItemDto itemDto) {
        if (userId != itemStorage.getItemBuId(id).getOwner().getId()) {
            log.info("Изменения пытается внесли не собственник вещи");
            throw new NotFoundException("Изменения в товар может вносить только его собственник");
        }
        User user = userStorage.getUserById(userId);
        Item item = ItemMapper.toItemWithId(id, user, itemDto);
        log.info("Обновлены данные вещи");
        return itemStorage.updateItem(userId, item);
    }

    /**
     * Метод возвращает вещь конкретного пользователя
     */
    @Override
    public Item getItem(long id, long userId) {
        userStorage.getUserById(userId);
        return itemStorage.getItemBuId(id);
    }

    /**
     * Метод возвращает все вещи конкретного пользователя
     */
    @Override
    public List<Item> getUserItems(long userId) {
        return itemStorage.getAllUserItem(userId);
    }

    /**
     * Метод поиска вещей
     */
    @Override
    public List<Item> searchItem(String text) {
        return itemStorage.searchingForItem(text);
    }

    /**
     * Метод проверки пользователя
     */
    private void checkUser(long userId) {
        try {
            userStorage.getUserById(userId);
        } catch (RuntimeException ex) {
            throw new NotFoundException("Такого пользователя не существует");
        }
    }
}
