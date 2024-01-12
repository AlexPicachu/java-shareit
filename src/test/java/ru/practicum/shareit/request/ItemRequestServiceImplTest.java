package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceImplTest {
    @InjectMocks
    private ItemRequestServiceImpl itemRequestService;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRequestRepository itemRequestRepository;

    private final LocalDateTime now = LocalDateTime.now();
    private User userOwner;
    private User userBooker;

    private Item item;

    @BeforeEach
    void setUp() {
        itemRequestService = new ItemRequestServiceImpl(itemRequestRepository, userRepository, itemRepository);
        userOwner = User.builder()
                .id(1L)
                .email("userOwner@yandex.ru")
                .name("userOwner")
                .build();

        userBooker = User.builder()
                .id(2L)
                .email("userBooker@yandex.ru")
                .name("userBooker")
                .build();


        item = Item.builder()
                .id(1L)
                .name("Дрель")
                .description("Очень хорошая дрель")
                .available(true)
                .owner(userOwner)
                .build();

    }

    @Test
    void addRequest_whenRequestFound_thenReturnRequest() {
        ItemRequestDto itemRequestDto = new ItemRequestDto("запрос на дрель", 2);
        ItemRequest itemRequest = new ItemRequest(1, "!!!", userBooker, now);
        when(userRepository.findById(userBooker.getId()))
                .thenReturn(Optional.of(userBooker));
        when(itemRequestRepository.save(any(ItemRequest.class)))
                .thenReturn(itemRequest);
        ItemRequest itemRequest1 = itemRequestService.addRequest(userBooker.getId(), itemRequestDto, now);
        assertEquals(userBooker.getId(), itemRequest1.requestor.getId(), "Неверно отработал метод");
        assertEquals("!!!", itemRequest1.getDescription(), "Неверно отработал метод");
        assertEquals(now, itemRequest1.getCreated());

    }

    @Test
    void addRequest_whenRequestNotFound_thenReturnThrows() {
        ItemRequestDto itemRequestDto = new ItemRequestDto("запрос на дрель", 2);
        when(userRepository.findById(userBooker.getId()))
                .thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, ()-> itemRequestService.addRequest(userBooker.getId(), itemRequestDto,now));
    }

    @Test
    void addRequest() {

    }


    @Test
    void getAllMyRequests() {
    }

    @Test
    void getListOfOtherUsersRequests() {
    }

    @Test
    void getItemRequest() {
    }
}