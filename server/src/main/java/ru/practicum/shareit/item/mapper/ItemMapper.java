package ru.practicum.shareit.item.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingShort;
import ru.practicum.shareit.item.comment.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithCommentsAndBookings;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.List;


/**
 * класс Mapper преобразовывает Item в ItemDto и обратно
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {

    /**
     * метод преобразовывает Item в ItemDto
     *
     * @param item - вещь из БД
     * @return - вещь в формате ItemDto
     */
    public static ItemDto toItemDto(Item item) {
        if (item.getRequest() == null) {
            item.setRequest(new ItemRequest());
        }
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .requestId(item.getRequest().getId())
                .build();

    }

    /**
     * метод преобразовывает ItemDto в Item
     *
     * @param user    - пользователь
     * @param itemDto - вещь в формате itemDto пришедшая от пользователя или возвращаемая на фронт
     * @param request - запрос на вещь
     * @return - вещь
     */
    public static Item toItem(User user, ItemDto itemDto, ItemRequest request) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .owner(user)
                .request(request)
                .build();
    }

    /**
     * метод преобразовывает ItemDto в Item
     *
     * @param id      - вещи
     * @param user    - пользователь
     * @param itemDto - вещь в формате itemDto пришедшая от пользователя или возвращаемая на фронт
     * @return - вещь
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

    /**
     * Метод преобразовывает item в ItemWithCommentsAndBookings
     *
     * @param item       - вещь
     * @param last       - бронирование
     * @param next       - след.бронирование
     * @param commentDto - список комментариев
     * @return - вещь в формате ItemWithCommentsAndBookings с комментариями и бронированием
     */
    public static ItemWithCommentsAndBookings toItemWithTime(Item item, BookingShort last, BookingShort next,
                                                             List<CommentDto> commentDto) {
        return ItemWithCommentsAndBookings.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .lastBooking(last)
                .nextBooking(next)
                .comments(commentDto)
                .build();

    }
}
