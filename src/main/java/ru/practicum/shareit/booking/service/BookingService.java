package ru.practicum.shareit.booking.service;


import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

/**
 * Контракт для реализации BookingServiceImpl
 */
public interface BookingService {
    /**
     * Метод добавления нового бронирования
     *
     * @param userId            - пользователя
     * @param bookingDtoRequest - запрос на бронирование
     * @return - возвращает добавленное в Б/Д бронирование
     */
    Booking addBooking(long userId, BookingDtoRequest bookingDtoRequest);

    /**
     * Метод подтверждение или отклонение запроса на бронирование. Может быть выполнено только владельцем вещи.
     *
     * @param bookingId - бронирования
     * @param userId    - пользователя
     * @param approved  - может принимать значения true или false
     * @return - возвращает подтверждение или отклоненное бронирование
     */
    Booking getStatus(long bookingId, long userId, Boolean approved);

    /**
     * Метод получения данных о конкретном бронировании, может быть выполнено либо автором бронирования, либо владельцем вещи
     *
     * @param bookingId - бронирования
     * @param userId    - пользователя
     * @return - возвращает бронирование
     */
    Booking getBooking(long bookingId, long userId);

    /**
     * Метод получения списка всех бронирований текущего пользователя
     *
     * @param userId - пользователя
     * @param status - статус
     * @return - список всех бронирований
     */
    List<Booking> getUserBookings(long userId, String status);

    /**
     * Метод получения списка бронирований для всех вещей текущего пользователя
     *
     * @param userId - пользователя
     * @param status -статус
     * @return - список всех бронирований
     */
    List<Booking> getUserItems(long userId, String status);

}
