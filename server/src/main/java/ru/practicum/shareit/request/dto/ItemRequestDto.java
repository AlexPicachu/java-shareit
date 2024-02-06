package ru.practicum.shareit.request.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Класс DTO - входящего запроса вещи
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Generated
@Data
public class ItemRequestDto {
    @NotBlank
    private String description;
    @NotNull
    private long requestor;
}
