package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;


/**
 * класс Mapper преобразовывает Item в ItemDto и обратно
 */
@AllArgsConstructor
public class ItemMapper {

    /**
     * метод преобразовывает Item в ItemDto
     */
    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();

    }

    /**
     * метод преобразовывает ItemDto в Item
     */
    public static Item toItem(User user, ItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .owner(user)
                .build();
    }

    /**
     * метод преобразовывает ItemDto в Item
     */
    public static Item toItemWithId(long id, User user, ItemDto itemDto) {
        return Item.builder()
                .id(id)
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .owner(user)
                .build();
    }
}
