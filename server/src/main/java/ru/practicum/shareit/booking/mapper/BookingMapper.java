package ru.practicum.shareit.booking.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResp;
import ru.practicum.shareit.booking.dto.BookingShort;
import ru.practicum.shareit.booking.enums.BookingStatus;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

/**
 * Класс Mapper, для преобразования Booking в ответ и запрос
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingMapper {

    /**
     * Метод преобразовывает Booking в BookingDtoResp
     *
     * @param booking - получаемый из Б/Д
     * @return - возвращает BookingDtoResp
     */
    public static BookingDtoResp toResponse(Booking booking) {

        ItemDto itemDto = ItemDto.builder()
                .id(booking.getItem().getId())
                .name(booking.getItem().getName())
                .build();

        UserDto owner = UserDto.builder()
                .id(booking.getBookingUser().getId())
                .build();

        return BookingDtoResp.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .item(itemDto)
                .booker(owner)
                .status(booking.getStatus())
                .build();
    }

    /**
     * Метод преобразовывает входные параметры в Booking
     *
     * @param bookingDtoRequest - сущность полученная от пользователя
     * @param item              - бронируемая вещь
     * @param user              - пользователь
     * @param bookingStatus     - статус бронируемой вещи
     * @return - возвращает Booking для записи в Б/Д
     */
    public static Booking toBooking(BookingDtoRequest bookingDtoRequest, Item item, User user, BookingStatus bookingStatus) {

        return Booking.builder()
                .start(bookingDtoRequest.getStart())
                .end(bookingDtoRequest.getEnd())
                .item(item)
                .bookingUser(user)
                .status(bookingStatus)
                .build();
    }

    /**
     * Метод для преобразования Booking в короткий ответ
     *
     * @param booking - сущность бронирования
     * @return - возвращает BookingShort
     */
    public static BookingShort toBookingShort(Booking booking) {

        return BookingShort.builder()
                .id(booking.getId())
                .bookerId(booking.getBookingUser().getId())
                .build();
    }
}
