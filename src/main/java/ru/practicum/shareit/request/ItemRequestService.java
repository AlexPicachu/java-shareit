package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Контракт для реализации ItemRequestServiceImpl
 */
public interface ItemRequestService {
    /**
     * Метод добавления новый запрос вещи
     *
     * @param userId         - пользователя
     * @param itemRequestDto - запрос с описанием вещи
     * @return - сформированный запрос в формате ItemRequest
     */
    ItemRequest addRequest(long userId, ItemRequestDto itemRequestDto, LocalDateTime localDateTime);


    /**
     * Метод получения списка своих запросов вместе с данными об ответах на них
     *
     * @param userId - пользователя
     * @return - список своих запросов с ответами в формате ItemRequestResponse
     */
    List<ItemRequestResponse> getAllMyRequests(long userId);

    /**
     * Метод получения запросов, созданных другими пользователями
     *
     * @param userId - пользователя
     * @param from   - индекс первого элемента
     * @param size   - количество элементов для отображения
     * @return - список запросов в формате ItemRequestResponse
     */
    List<ItemRequestResponse> getListOfOtherUsersRequests(long userId, Integer from, Integer size);

    /**
     * Метод получения данные об одном конкретном запросе вместе с данными об ответах на него
     *
     * @param requestId - запроса
     * @param userId    - пользователя
     * @return возвращает запрос в формате ItemRequestResponse
     */
    ItemRequestResponse getItemRequest(long requestId, long userId);

}
