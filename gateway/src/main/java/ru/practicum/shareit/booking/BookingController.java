package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


/**
 * класс валидации входных параметров
 */
@RestController
@RequestMapping(path = "/bookings")
@AllArgsConstructor
@Validated
@Slf4j
public class BookingController {
    private static final String USER_ID = "X-Sharer-User-Id";

    private final BookingClient bookingClient;


    /**
     * Метод валидации входящих параметров, перед добавлением нового бронирования
     */
    @PostMapping
    public ResponseEntity<Object> addBooking(@RequestHeader(USER_ID) long userId,
                                             @Valid @RequestBody BookingDtoRequest bookingDtoRequest) {
        log.info("Создано бронирование по пользователю с id {}", userId);
        return bookingClient.addBooking(userId, bookingDtoRequest);
    }

    /**
     * Метод валидации входящих параметров, перед подтверждением или отклонением запроса на бронирование. Может быть выполнено только владельцем вещи.
     */
    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> confirmationOrRejectionOfBooking(
            @PathVariable long bookingId, @RequestHeader(USER_ID) long userId, @RequestParam(name = "approved")
    Boolean approved) {
        if (approved) {
            log.info("Бронирование с bookingId {} , принято.", bookingId);
        } else {
            log.info("Бронирование с bookingId {} , отклонено.", bookingId);
        }
        return bookingClient.getStatus(userId, bookingId, approved);
    }

    /**
     * Метод валидации входящих параметров, перед получением данных о конкретном бронировании, может быть выполнено либо автором бронирования, либо владельцем вещи
     */
    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@PathVariable long bookingId, @RequestHeader(USER_ID) long userId) {
        log.info("бронирование с bookingId {}, пользователя с userId  {}", bookingId, userId);
        return bookingClient.getBooking(userId, bookingId);
    }

    /**
     * Метод валидации входящих параметров, перед получением списка всех бронирований текущего пользователя
     */
    @GetMapping
    public ResponseEntity<Object> getAllBookingsUser(@RequestHeader(USER_ID) long userId,
                                                     @RequestParam(defaultValue = "ALL") String state,
                                                     @Valid @RequestParam(defaultValue = "1") @Min(1) Integer from,
                                                     @Valid @RequestParam(defaultValue = "20") @Min(1) @Max(20) Integer size) {
        log.info("все бронирования пользователя с userId {} получены", userId);
        return bookingClient.getUserBookings(userId, state, from, size, false);
    }

    /**
     * Метод валидации входящих параметров, перед получением списка бронирований для всех вещей текущего пользователя
     */
    @GetMapping("/owner")
    public ResponseEntity<Object> getAllItemsUser(@RequestHeader(USER_ID) long userId,
                                                  @RequestParam(defaultValue = "ALL") String state,
                                                  @Valid @RequestParam(defaultValue = "1") @Min(1) Integer from,
                                                  @Valid @RequestParam(defaultValue = "20") @Min(1) @Max(20) Integer size) {
        log.info("получены все бронирования собственника с userId {}, на его вещи", userId);
        return bookingClient.getUserBookings(userId, state, from, size, true);
    }
}
