package ru.practicum.shareit.user.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Test
    void getAllUsers() {
    }

    @Test
    void createUserTest_whenUserNameValid_thenSavedUser() {
        User userToSave = new User();
        when(userRepository.save(userToSave))
                .thenReturn(userToSave);
        User actualUser = userService.createUser(userToSave);

        assertEquals(userToSave, actualUser, "Метод отработал некорректно");
        verify(userRepository).save(userToSave);
    }


    @Test
    void updateUser() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void deleteUser() {
    }
}