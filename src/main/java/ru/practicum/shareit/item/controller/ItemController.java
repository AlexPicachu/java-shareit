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
     */
    @PostMapping
    public ItemDto addNewItem(@RequestHeader(USER_ID) long userId, @Valid @RequestBody ItemDto itemDto) {
        Item item = itemService.addItem(userId, itemDto);
        return ItemMapper.toItemDto(item);
    }

    /**
     * Метод обновляет вещь у конкретного пользователя
     * * id вещи
     * userId пользователя
     */
    @PatchMapping("/{id}")
    public ItemDto updateItem(@PathVariable long id, @RequestHeader(USER_ID) long userId,
                              @RequestBody ItemDto itemDto) {
        Item item = itemService.updateItem(id, userId, itemDto);
        return ItemMapper.toItemDto(item);
    }

    /**
     * Метод возвращает вещь конкретного пользователя и комментарии к ней
     * id вещи
     * userId пользователя
     */
    @GetMapping("/{id}")
    public ItemWithCommentsAndBookings getItemById(@PathVariable long id, @RequestHeader(USER_ID) long userId) {
        return itemService.getItem(id, userId);
    }

    /**
     * Метод возвращает список вещей и комментарии к ним, пользователя по его id
     */
    @GetMapping
    public List<ItemWithCommentsAndBookings> getUserAllItems(@RequestHeader(USER_ID) Long userId) {
        return itemService.getUserItems(userId);
    }

    /**
     * Метод возвращает список найденный вещей по запросу переданному в аргументе text
     */
    @GetMapping("/search")
    public List<ItemDto> searchItem(@RequestParam(name = "text") String text) {
        return itemService.searchItem(text).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    /**
     * Метод создает новый комментарий и возвращает его в формате commentDto
     */
    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@PathVariable long itemId,
                                 @RequestHeader(USER_ID) long userId, @Valid @RequestBody CommentDtoInput commentDtoInput) {
        return CommentMapper.toCommentDto(itemService.addComment(userId, itemId, commentDtoInput));
    }


}
