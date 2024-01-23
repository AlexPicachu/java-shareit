package ru.practicum.shareit.item.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.comment.CommentDto;
import ru.practicum.shareit.item.comment.CommentDtoInput;
import ru.practicum.shareit.item.comment.CommentMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithCommentsAndBookings;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Класс контроллер, для принятия запросов сущности Item
 */
@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {

    private static final String USER_ID = "X-Sharer-User-Id";

    private final ItemService itemService;


    /**
     * Метод добавляет новую вещь
     *
     * @param userId  - пользователя
     * @param itemDto - входные параметры новой вещи в формате itemDto
     * @return - сохраненную вещь в формате itemDto
     */
    @PostMapping
    public ItemDto addNewItem(@RequestHeader(USER_ID) long userId, @Valid @RequestBody ItemDto itemDto) {
        Item item = itemService.addItem(userId, itemDto);
        return ItemMapper.toItemDto(item);
    }

    /**
     * Метод обновляет вещь у конкретного пользователя
     *
     * @param id      - вещи
     * @param userId  - пользователя
     * @param itemDto - входные параметры для обновления существующей вещи в формате itemDto
     * @return - обновленную вещь в формате itemDto
     */
    @PatchMapping("/{id}")
    public ItemDto updateItem(@PathVariable long id, @RequestHeader(USER_ID) long userId,
                              @RequestBody ItemDto itemDto) {
        Item item = itemService.updateItem(id, userId, itemDto);
        return ItemMapper.toItemDto(item);
    }

    /**
     * Метод возвращает вещь конкретного пользователя и комментарии к ней
     *
     * @param id     - вещи
     * @param userId - пользователя
     * @return - вещь в формате ItemWithCommentsAndBookings
     */
    @GetMapping("/{id}")
    public ItemWithCommentsAndBookings getItemById(@PathVariable long id, @RequestHeader(USER_ID) long userId) {
        return itemService.getItem(id, userId);
    }


    /**
     * Метод возвращает список вещей и комментарии к ним, пользователя по его id
     *
     * @param userId - пользователя
     * @param from   - номер страницы
     * @param size   - длина страницы
     * @return список вещей в формате ItemWithCommentsAndBookings
     */
    @GetMapping
    public List<ItemWithCommentsAndBookings> getUserAllItems(@RequestHeader(USER_ID) Long userId,
                                                             @RequestParam(defaultValue = "1") @Min(1) Integer from,
                                                             @RequestParam(defaultValue = "20") @Min(1) @Max(20) Integer size) {
        return itemService.getUserItems(userId, from, size);
    }


    /**
     * Метод возвращает список найденный вещей по запросу переданному в аргументе text
     *
     * @param text - текст запроса по которому осуществляется поиск
     * @param from - номер страницы
     * @param size - длина страницы
     * @return - список вещей в формате ItemDto
     */
    @GetMapping("/search")
    public List<ItemDto> searchItem(@RequestParam(name = "text") String text,
                                    @RequestParam(defaultValue = "1") @Min(1) Integer from,
                                    @RequestParam(defaultValue = "20") @Min(1) @Max(20) Integer size) {
        return itemService.searchItem(text, from, size).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    /**
     * Метод создает новый комментарий и возвращает его в формате commentDto
     *
     * @param itemId          - вещи
     * @param userId          - пользователя
     * @param commentDtoInput - комментарий для добавления
     * @return - сохраненный комментарий в формате CommentDto
     */
    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@PathVariable long itemId,
                                 @RequestHeader(USER_ID) long userId, @Valid @RequestBody CommentDtoInput commentDtoInput) {
        return CommentMapper.toCommentDto(itemService.addComment(userId, itemId, commentDtoInput));
    }


}
