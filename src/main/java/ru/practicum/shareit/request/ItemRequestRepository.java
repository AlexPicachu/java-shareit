package ru.practicum.shareit.request;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.dto.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.util.List;

/**
 * Контракт для реализации кастомных методов JpaRepository
 */
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    List<ItemRequest> findAllByRequestorOrderByCreatedDesc(User user);

    List<ItemRequest> findAllByRequestorNotOrderByCreatedDesc(User user, Pageable pageable);
}
