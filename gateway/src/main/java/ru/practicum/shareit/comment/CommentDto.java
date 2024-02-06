package ru.practicum.shareit.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Класс для формирования ответа пользователю
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    @NotNull
    private long id;
    private String text;
    private String authorName;
    private LocalDateTime created;
}
