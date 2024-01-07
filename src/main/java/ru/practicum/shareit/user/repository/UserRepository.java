package ru.practicum.shareit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.user.model.User;

/**
 * Контракт для реализации JpaRepository
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
