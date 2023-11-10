package net.breezeware.cosspringproject.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.breezeware.cosspringproject.order.entity.Order;
import net.breezeware.cosspringproject.order.entity.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
}
