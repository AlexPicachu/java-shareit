package ru.practicum.shareit.item.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO Sprint add-controllers.
 */

@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;

    private static final String USER_ID = "X-Sharer-User-Id";

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
     * Метод возвращает вещь конкретного пользователя
     * id вещи
     * userId пользователя
     */
    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable long id, @RequestHeader(USER_ID) long userId) {
        Item item = itemService.getItem(id, userId);
        return ItemMapper.toItemDto(item);
    }

    /**
     * Метод возвращает список вещей пользователя по его id
     */
    @GetMapping
    public List<ItemDto> getUserAllItems(@RequestHeader(USER_ID) long userId) {
        return itemService.getUserItems(userId).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
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

}
