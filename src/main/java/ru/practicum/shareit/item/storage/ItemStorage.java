package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;


import java.util.List;

/**
 * контракт для реализации ItemStorageImpl
 */
public interface ItemStorage {

    /**
     * добавит новую вещь
     */
    Item create(Item item);

    /**
     * вернуть список вещей пользователя по его id
     */
    List<Item> getAllUserItem(long userId);


    /**
     * обновить существующую вещь
     */
    Item updateItem(long userId, Item item);

    /**
     * поиск вещи по названию
     */
    List<Item> searchingForItem(String text);

    /**
     * возвращает вещь по id
     */
    Item getItemBuId(long id);

}
