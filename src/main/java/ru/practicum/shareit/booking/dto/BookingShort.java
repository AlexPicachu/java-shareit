package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Класс для короткого ответа пользователю
 */
@Data
@Builder
public class BookingShort {

    private long id;
    private long bookerId;
}
