package ru.practicum.shareit.exeption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    /**
     * метод обработки исключения, возвращающий getMessage() и ошибку 400
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        log.info("Произошла ошибка 400 при сверке данный");
        return new ErrorResponse(e.getMessage());
    }

    /**
     * метод обработки исключения, возвращающий getMessage() и ошибку 404
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.info("Произошла ошибка 404 при поиске переданных параметров в хранилище");
        return new ErrorResponse(e.getMessage());
    }
}
