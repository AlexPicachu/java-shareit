package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.user.dto.UserDto;

/**
 * класс обработки запросов по адресу localhost:9090/bookings
 */
@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    /**
     * метод, в случае удачной валидации, отправляет запрос для просмотра всех пользователей
     */
    public ResponseEntity<Object> getAllUser() {
        return get("");
    }

    /**
     * метод, в случае удачной валидации, отправляет запрос для добавления нового бронирования
     */
    public ResponseEntity<Object> createUser(UserDto userDto) {
        return post("", userDto);
    }

    /**
     * метод, в случае удачной валидации, отправляет запрос для обновления данных пользователя
     */
    public ResponseEntity<Object> updateUserById(long id, UserDto userDto) {
        return patch("/" + id, userDto);
    }

    /**
     * метод, в случае удачной валидации, отправляет запрос для удаления пользователя по id
     */
    public ResponseEntity<Object> deleteUserById(long id) {
        return delete("/" + id);
    }

    /**
     * метод, в случае удачной валидации, отправляет запрос для просмотра данных пользователя по id
     */
    public ResponseEntity<Object> getUserById(long id) {
        return get("/" + id);
    }
}
