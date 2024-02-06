package ru.practicum.shareit.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Класс для формирования входящих комментариев
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDtoInput {
    @NotBlank
    private String text;
}
