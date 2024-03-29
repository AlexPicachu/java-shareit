package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.CommentDtoInput;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithCommentsAndBookings;
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
     * Метод возвращает вещь конкретного пользователя
     */
    ItemWithCommentsAndBookings getItem(long id, long userId);

    /**
     * Метод возвращает все вещи конкретного пользователя
     */
    List<ItemWithCommentsAndBookings> getUserItems(Long userId, Integer from, Integer size);

    /**
     * Метод поиска вещей
     */
    List<Item> searchItem(String text, Integer from, Integer size);

    /**
     * Метод для создания нового комментария
     */
    Comment addComment(long userId, long itemId, CommentDtoInput commentDtoInput);
}
