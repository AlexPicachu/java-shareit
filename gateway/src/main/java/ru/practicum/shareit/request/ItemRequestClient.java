package ru.practicum.shareit.request;

import ru.practicum.shareit.client.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.Map;

/**
 * класс обработки запросов по адресу localhost:9090/requests
 */
@Service
public class ItemRequestClient extends BaseClient {

    private static final String API_PREFIX = "/requests";

    @Autowired
    public ItemRequestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    /**
     * метод, в случае удачной валидации, отправляет запрос для добавления нового запроса
     */
    public ResponseEntity<Object> addRequest(long userId, ItemRequestDto itemRequestDto) {
        return post("", userId, itemRequestDto);
    }

    /**
     * метод, в случае удачной валидации, отправляет запрос для просмотра всех запросов конкретного пользователя
     */
    public ResponseEntity<Object> getAllMyRequests(long userId) {
        return get("", userId);
    }

    /**
     * метод, в случае удачной валидации, отправляет запрос для просмотра запросов других пользователей
     */
    public ResponseEntity<Object> getListOfOtherUsersRequests(long userId, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("/all?from={from}&size={size}", userId, parameters);
    }

    /**
     * метод, в случае удачной валидации, отправляет запрос для получения ответов на запрос
     */
    public ResponseEntity<Object> getItemRequest(long requestId, long userId) {
        return get("/" + requestId, userId);
    }

}
