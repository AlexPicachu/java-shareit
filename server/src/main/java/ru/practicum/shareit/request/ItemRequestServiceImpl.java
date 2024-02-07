package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.request.dto.ItemRequestResponse;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import org.springframework.data.domain.Pageable;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс реализующий логику интерфейса ItemRequestService
 */
@Service
@Slf4j
@AllArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    /**
     * Метод добавления нового запроса на вещь
     *
     * @param userId         - пользователя
     * @param itemRequestDto - запрос с описанием вещи
     * @param localDateTime  - дата добавления запроса
     * @return - новый запрос
     */
    @Override
    public ItemRequest addRequest(long userId, ItemRequestDto itemRequestDto, LocalDateTime localDateTime) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с таким id = " + userId + "  не существует"));
        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto, user, localDateTime);
        log.info("Добавлен новый запрос {}", itemRequest);
        return itemRequestRepository.save(itemRequest);
    }

    /**
     * Метод для получения списка своих запросов вместе с данными об ответах на них
     *
     * @param userId - пользователя
     * @return - список своих запросов
     */
    @Override
    public List<ItemRequestResponse> getAllMyRequests(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с таким id = " + userId + "  не существует"));
        return itemRequestRepository.findAllByRequestorOrderByCreatedDesc(user)
                .stream()
                .map(itemRequest -> ItemRequestMapper.toItemRequestResponse(itemRequest, findItems(itemRequest)))
                .collect(Collectors.toList());
    }

    /**
     * Метод получения списка запросов, созданных другими пользователями
     *
     * @param userId - пользователя
     * @param from   - индекс первого элемента
     * @param size   - количество элементов для отображения
     * @return - список запросов
     */
    @Override
    public List<ItemRequestResponse> getListOfOtherUsersRequests(long userId, Integer from, Integer size) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с таким id = " + userId + "  не существует"));
        Pageable page = PageRequest.of(from, size);
        List<ItemRequest> requests = itemRequestRepository.findAllByRequestorNotOrderByCreatedDesc(user, page);
        log.info("получен список запросов, созданных другими пользователями {}", requests);
        return requests.stream()
                .map(itemRequest -> ItemRequestMapper.toItemRequestResponse(itemRequest, findItems(itemRequest)))
                .collect(Collectors.toList());
    }

    /**
     * Метод получения данных об одном конкретном запросе вместе с данными об ответах на него
     *
     * @param requestId - запроса
     * @param userId    - пользователя
     * @return - запрос в формате ItemRequestResponse
     */
    @Override
    public ItemRequestResponse getItemRequest(long requestId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с таким id = " + userId + "  не существует"));
        ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запроса с таким id = " + requestId + "  не существует"));
        return ItemRequestMapper.toItemRequestResponse(itemRequest, findItems(itemRequest));
    }

    /**
     * Метод преобразовывает Item в ItemDto
     *
     * @param request - запрос
     * @return - список ItemDto
     */
    private List<ItemDto> findItems(ItemRequest request) {
        return itemRepository.findAllByRequest(request).stream()
                .map(item -> ItemMapper.toItemDto(item))
                .collect(Collectors.toList());
    }
}
