package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestResponse;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Класс контроллер для обработки запросов ItemRequest
 */
@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
public class ItemRequestController {

    private static final String USER_ID = "X-Sharer-User-Id";

    private final ItemRequestService itemRequestService;

    /**
     * Метод добавления новый запрос вещи
     *
     * @param userId         - пользователя
     * @param itemRequestDto - запрос с описанием вещи
     * @return - сформированный запрос в формате ItemRequest
     */
    @PostMapping
    public ItemRequest addRequest(@RequestHeader(USER_ID) long userId,
                                  @Valid @RequestBody ItemRequestDto itemRequestDto) {
        LocalDateTime now = LocalDateTime.now();
        return itemRequestService.addRequest(userId, itemRequestDto, now);
    }

    /**
     * Метод получения списка своих запросов вместе с данными об ответах на них
     *
     * @param userId - пользователя
     * @return - список своих запросов с ответами в формате ItemRequestResponse
     */
    @GetMapping
    public List<ItemRequestResponse> getAllMyRequests(@RequestHeader(USER_ID) long userId) {
        return itemRequestService.getAllMyRequests(userId);
    }

    /**
     * Метод получения запросов, созданных другими пользователями
     *
     * @param userId - пользователя
     * @param from   - индекс первого элемента
     * @param size   - количество элементов для отображения
     * @return - список запросов в формате ItemRequestResponse
     */
    @GetMapping("/all")
    public List<ItemRequestResponse> getListOfOtherUsersRequests(@RequestHeader(USER_ID) long userId,
                                                                 @RequestParam(defaultValue = "1") @Min(1) Integer from,
                                                                 @RequestParam(defaultValue = "20") @Min(1) @Max(20) Integer size) {

        return itemRequestService.getListOfOtherUsersRequests(userId, from, size);
    }

    /**
     * Метод получения данные об одном конкретном запросе вместе с данными об ответах на него
     *
     * @param requestId - запроса
     * @param userId    - пользователя
     * @return возвращает запрос в формате ItemRequestResponse
     */
    @GetMapping("/{requestId}")
    public ItemRequestResponse getItemRequest(@PathVariable long requestId, @RequestHeader(USER_ID) long userId) {
        return itemRequestService.getItemRequest(requestId, userId);
    }

}
