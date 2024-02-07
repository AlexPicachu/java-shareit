package ru.practicum.shareit.request.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import java.util.List;

/**
 * Класс Mapper для преобразования ItemRequest
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRequestMapper {

    /**
     * Метод преобразования входящих параметров в ItemRequest
     *
     * @param itemRequestDto - входящий запрос в формате itemRequestDto
     * @param user           - пользователь
     * @param localDateTime  - время создания запроса
     * @return
     */
    public static ItemRequest toItemRequest(ItemRequestDto itemRequestDto, User user, LocalDateTime localDateTime) {

        return ItemRequest.builder()
                .description(itemRequestDto.getDescription())
                .requestor(user)
                .created(localDateTime)
                .build();
    }

    /**
     * преобразовывает входящие параметры в ответ пользователю(на фронт)
     *
     * @param itemRequest - запрос на вещь
     * @param itemDtoList - список вещей в формате ItemDto
     * @return - ответ в формате ItemRequestResponse
     */
    public static ItemRequestResponse toItemRequestResponse(ItemRequest itemRequest, List<ItemDto> itemDtoList) {

        return ItemRequestResponse.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .items(itemDtoList)
                .build();
    }
}
