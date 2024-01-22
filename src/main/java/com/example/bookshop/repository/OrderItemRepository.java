package com.example.bookshop.repository;

import com.example.bookshop.model.OrderItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @EntityGraph(attributePaths = {"order", "book"})
    Optional<OrderItem> findByOrderIdAndId(Long itemId, Long orderId);

    List<OrderItem> findAllByOrderId(Long orderId, Pageable pageable);
}
