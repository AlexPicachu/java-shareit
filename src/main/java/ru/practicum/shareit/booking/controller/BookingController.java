package ru.practicum.shareit.booking.controller;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingDtoResp;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

/**
 * класс контроллер для обработки запросов бронирования
 */
@RestController
@RequestMapping(path = "/bookings")
@AllArgsConstructor
@Validated
public class BookingController {
    private static final String USER_ID = "X-Sharer-User-Id";

    private final BookingService bookingService;


    /**
     * Метод добавления нового запроса на бронирование
     *
     * @param userId            - пользователя
     * @param bookingDtoRequest - запрос на бронирование
     * @return - ответ в формате BookingDtoResp
     */
    @PostMapping
    public BookingDtoResp addBooking(@RequestHeader(USER_ID) long userId,
                                     @Valid @RequestBody BookingDtoRequest bookingDtoRequest) {
        Booking booking = bookingService.addBooking(userId, bookingDtoRequest);
        return BookingMapper.toResponse(booking);
    }

    /**
     * Метод подтверждение или отклонение запроса на бронирование. Может быть выполнено только владельцем вещи.
     *
     * @param bookingId - бронирования
     * @param userId    - пользователя
     * @param approved  - может принимать значения true или false
     * @return - ответ в формате BookingDtoResp
     */
    @PatchMapping("/{bookingId}")
    public BookingDtoResp confirmationOrRejectionOfBooking(
            @PathVariable long bookingId, @RequestHeader(USER_ID) long userId, @RequestParam(name = "approved")
    Boolean approved) {
        Booking booking = bookingService.getStatus(bookingId, userId, approved);
        return BookingMapper.toResponse(booking);
    }

    /**
     * Метод получения данных о конкретном бронировании, может быть выполнено либо автором бронирования, либо владельцем вещи
     *
     * @param bookingId - бронирования
     * @param userId    - пользователя
     * @return - ответ в формате BookingDtoResp
     */
    @GetMapping("/{bookingId}")
    public BookingDtoResp getBooking(@PathVariable long bookingId, @RequestHeader(USER_ID) long userId) {
        Booking booking = bookingService.getBooking(bookingId, userId);
        return BookingMapper.toResponse(booking);
    }

    /**
     * Метод получения списка всех бронирований текущего пользователя
     *
     * @param userId - пользователя
     * @param state  -статус
     * @return - список всех бронирований в формате BookingDtoResp
     */
    @GetMapping
    public List<BookingDtoResp> getAllBookingsUser(@RequestHeader(USER_ID) long userId,
                                                   @RequestParam(defaultValue = "ALL") String state,
                                                   @Valid @RequestParam(defaultValue = "1") @Min(1) Integer from,
                                                   @Valid @RequestParam(defaultValue = "20") @Min(1) @Max(20) Integer size){
        List<Booking> bookings = bookingService.getUserBookings(userId, state, from, size);
        return bookings.stream()
                .map(BookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Метод получения списка бронирований для всех вещей текущего пользователя
     *
     * @param userId - пользователя
     * @param state  -статус
     * @return - список всех бронирований в формате BookingDtoResp
     */
    @GetMapping("/owner")
    public List<BookingDtoResp> getAllItemsUser(@RequestHeader(USER_ID) long userId,
                                                @RequestParam(defaultValue = "ALL") String state,
                                                @Valid @RequestParam(defaultValue = "1") @Min(1) Integer from,
                                                @Valid @RequestParam(defaultValue = "20") @Min(1) @Max(20) Integer size) {
        List<Booking> bookings = bookingService.getUserItems(userId, state, from, size);
        return bookings.stream()
                .map(BookingMapper::toResponse)
                .collect(Collectors.toList());
    }
}
