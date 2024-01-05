package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingShort;
import ru.practicum.shareit.item.comment.CommentDto;

import java.util.List;

/**
 * Класс для формирования ответа пользователю
 */
@Data
@Builder
public class ItemWithCommentsAndBookings {
    private long id;
    private String name;
    private String description;
    private Boolean available;
    private BookingShort lastBooking;
    private BookingShort nextBooking;
    private List<CommentDto> comments;

}
