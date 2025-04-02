package org.example.repository.order;

import org.example.model.Order;
import org.example.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUserId(Long userId, Pageable pageable);
    Optional<Order> findByIdAndUserId(Long orderId, Long userId);
    @Query("SELECT oi FROM OrderItem oi JOIN oi.order o "
            + "WHERE o.id = :orderId AND o.user.id = :userId")
    Page<OrderItem> findOrderItemsByOrderIdAndUserId(
            @Param("orderId") Long orderId, @Param("userId") Long userId, Pageable pageable);
}
