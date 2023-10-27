package net.breezeware.cosspringproject.order.dao;

import net.breezeware.cosspringproject.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
