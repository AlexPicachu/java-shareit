package ru.practicum.shareit.user.model;

import lombok.Builder;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */

/**
 * Модель пользователя
 */
@Data
@Builder
public class User {
    private long id;
    private String email;
    private String name;
}
