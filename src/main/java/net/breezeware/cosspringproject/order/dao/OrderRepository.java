package net.breezeware.cosspringproject.order.dao;

import net.breezeware.cosspringproject.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> getOrderByStatus(String status);
}
