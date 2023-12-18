package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

/**
 * контракт для реализации ItemServiceImpl
 */
public interface ItemService {

    /**
     * Метод добавляет вещь
     */
    Item addItem(long userId, ItemDto itemDto);

    /**
     * Метод обновления вещи
     */
    Item updateItem(long id, long userId, ItemDto item);

    /**
     * Метод возвращает вещт конкретного пользователя
     */
    Item getItem(long id, long userId);

    /**
     * Метод возвращает все вещи конкретного пользователя
     */
    List<Item> getUserItems(long userId);

    /**
     * Метод поиска вещей
     */
    List<Item> searchItem(String text);
}
