package ru.practicum.shareit.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @SneakyThrows
    @Test
    void getAllUserTest() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .email("alex@yandex.ru")
                .name("alex")
                .build();
        UserDto userDto1 = UserDto.builder()
                .id(2L)
                .email("alexander@yandex.ru")
                .name("alexander")
                .build();
        List<UserDto> users = List.of(userDto, userDto1);

        when(userService.getAllUsers())
                .thenReturn(users.stream().map(UserMapper::dtoToUser).collect(Collectors.toList()));
        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(userDto.getId()))
                .andExpect(jsonPath("$[1].name").value(userDto1.getName()));


    }

    @SneakyThrows
    @Test
    void createUser_whenInputUserValid_thenReturnSavesUser() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .email("alex@yandex.ru")
                .name("alex")
                .build();

        when(userService.createUser(UserMapper.dtoToUser(userDto)))
                .thenReturn(UserMapper.dtoToUser(userDto));

        mockMvc.perform(post("/users", userDto)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userDto.getName()));
        verify(userService).createUser(UserMapper.dtoToUser(userDto));
    }

    @SneakyThrows
    @Test
    void createUser_whenInputUserNotValid_thenReturnThrows() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .email(null)
                .name("alex")
                .build();
        mockMvc.perform(post("/users", userDto)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
        verify(userService, never()).createUser(UserMapper.dtoToUser(userDto));

    }

    @SneakyThrows
    @Test
    void updateUserById() {
        long userId = 1L;
        UserDto userDto = UserDto.builder()
                .id(1L)
                .email(null)
                .name("alex")
                .build();
        User user = UserMapper.dtoToUser(userDto);
        when(userService.updateUser(UserMapper.userDtoToUserByid(userId, userDto)))
                .thenReturn(UserMapper.dtoToUser(userDto));
        mockMvc.perform(patch("/users/{id}", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userDto.getName()));
        verify(userService, times(1)).updateUser(UserMapper.userDtoToUserByid(userId, userDto));
    }

    @SneakyThrows
    @Test
    void deleteUserByIdTest() {
        long id = 1L;
        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUser(id);
    }

    @Test
    void getUserById_whenUserFound_thenReturnUser() throws Exception {
        User user = User.builder()
                .id(1L)
                .email("alex@yandex.ru")
                .name("alex")
                .build();
        UserDto userDto = UserMapper.userToDto(user);

        when(userService.getUserById(user.getId()))
                .thenReturn(user);

        mockMvc.perform(get("/users/{id}", user.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userDto.getId()))
                .andExpect(jsonPath("$.name").value(userDto.getName()))
                .andExpect(jsonPath("$.email").value(userDto.getEmail()));
        verify(userService).getUserById(user.getId());
    }
}