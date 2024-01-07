package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingShort;
import ru.practicum.shareit.booking.enums.BookingStatus;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.item.comment.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithCommentsAndBookings;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * класс реализовывающий interface ItemService
 */
@Slf4j
@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;

    private final BookingRepository bookingRepository;

    /**
     * Метод добавляет новую вещь
     *
     * @param userId  - пользователя
     * @param itemDto - сущность item в формате itemDto
     * @return - сохраненную вещь
     */
    @Override
    public Item addItem(long userId, ItemDto itemDto) {
        checkUser(userId);
        User user = userService.getUserById(userId);
        Item item = ItemMapper.toItem(user, itemDto);
        return itemRepository.save(item);
    }

    /**
     * Метод для обновления данных вещи
     *
     * @param id      - вещи
     * @param userId  - пользователя
     * @param itemDto - сущность item в формате itemDto
     * @return - обновленную вещь
     */
    @Override
    @Transactional
    public Item updateItem(long id, long userId, ItemDto itemDto) {
        Item oldItem = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Вещи с таким id = " + id + "  не существует"));
        if (userId != oldItem.getOwner().getId()) {
            log.info("Изменения пытается внесли не собственник вещи");
            throw new NotFoundException("Изменения в товар может вносить только его собственник");
        }
        User user = userService.getUserById(userId);
        Item item = ItemMapper.toItemWithId(id, user, itemDto);
        if (item.getName() == null) {
            item.setName(oldItem.getName());
        }
        if (item.getDescription() == null) {
            item.setDescription(oldItem.getDescription());
        }
        if (item.getAvailable() == null) {
            item.setAvailable(oldItem.getAvailable());
        }
        itemRepository.save(item);
        log.info("Обновлены данные вещи");
        return item;
    }


    /**
     * Метод возвращает вещь конкретного пользователя
     *
     * @param id     - вещи
     * @param userId - пользователя
     * @return - возвращает вещь или пробрасывает ошибку
     */
    @Override
    public ItemWithCommentsAndBookings getItem(long id, long userId) {
        checkUser(userId);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Вещи с таким id = " + id + "  не существует"));
        return addNewEntity(item, userId);
    }

    /**
     * Метод возвращает все вещи одного пользователя
     *
     * @param userId - пользователя
     * @return - список вещей
     */
    @Override
    public List<ItemWithCommentsAndBookings> getUserItems(Long userId) {
        User user = userService.getUserById(userId);
        return itemRepository.findAllByOwner(user)
                .stream()
                .map(item -> addNewEntity(item, userId))
                .collect(Collectors.toList());

    }

    /**
     * Метод для поиска вещей по названию или описанию
     *
     * @param text - название или описание
     * @return - список вещей
     */
    @Override
    public List<Item> searchItem(String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
        return itemRepository.findAll()
                .stream()
                .filter(item -> (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(text.toLowerCase())))
                .filter(item -> item.getAvailable().equals(true))
                .collect(Collectors.toList());
    }

    @Override
    public Comment addComment(long userId, long itemId, CommentDtoInput commentDtoInput) {
        checkUser(userId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещи с таким id = " + itemId + "  не существует"));
        User user = userService.getUserById(userId);
        Comment comment;
        if (bookingRepository.findFirstByBookingUserIdAndItemIdAndEndIsBeforeOrderByEndDesc(userId, itemId, LocalDateTime.now()) != null) {
            comment = CommentMapper.toComment(commentDtoInput, item, user);
        } else {
            throw new ValidationException("Пользователь не может оставить комментарий");
        }
        return commentRepository.save(comment);

    }

    /**
     * Метод проверки пользователя
     */
    private void checkUser(long userId) {
        try {
            userService.getUserById(userId);
        } catch (RuntimeException ex) {
            throw new NotFoundException("Такого пользователя не существует");
        }
    }

    /**
     * Метод для добавления в item новых сущностей
     *
     * @param item   - вещь
     * @param userId - пользователя
     * @return - возвращает вещь с добавленными комментариями и бронированием
     */
    private ItemWithCommentsAndBookings addNewEntity(Item item, long userId) {
        LocalDateTime now = LocalDateTime.now();
        Booking lastBook = bookingRepository.findFirstByItemIdAndStartIsBeforeOrStartEqualsOrderByStartDesc(item.getId(), now, now);
        Booking nextBook = bookingRepository.findFirstByItemIdAndStartIsAfterOrderByStart(item.getId(), now);
        BookingShort last;
        BookingShort next;
        if (lastBook != null) {
            last = BookingMapper.toBookingShort(lastBook);
            if (lastBook.getStatus() == BookingStatus.REJECTED) {
                last = null;
            }
        } else {
            last = null;
        }
        if (nextBook != null) {
            next = BookingMapper.toBookingShort(nextBook);
            if (nextBook.getStatus() == BookingStatus.REJECTED) {
                next = null;
            }
        } else {
            next = null;
        }
        if (userId != item.getOwner().getId()) {
            last = null;
            next = null;
        }
        List<CommentDto> comments = commentRepository.findAllByItem(item).stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
        if (comments.isEmpty()) {
            comments = new ArrayList<>();
        }
        return ItemMapper.toItemWithTime(item, last, next, comments);
    }

}
