package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * класс DTO(Data Transfer Object)
 */
@Data
@Builder(toBuilder = true)
public class UserDto {

    private long id;
    @Email
    @NotNull
    private String email;
    @NotBlank
    private String name;
}
