package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * TODO Sprint add-controllers.
 */

/**
 * класс DTO(Data Transfer Object)
 */
@Data
@Builder(toBuilder = true)
public class ItemDto {

    private long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Boolean available;
}
