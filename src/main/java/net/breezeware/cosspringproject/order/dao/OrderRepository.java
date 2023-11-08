package net.breezeware.cosspringproject.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.breezeware.cosspringproject.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrderByStatus(String status);
}
