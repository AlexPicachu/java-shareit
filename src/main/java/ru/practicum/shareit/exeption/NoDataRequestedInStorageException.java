package ru.practicum.shareit.exeption;

public class NoDataRequestedInStorageException extends RuntimeException {
    public NoDataRequestedInStorageException(String message) {
        super(message);
    }
}
