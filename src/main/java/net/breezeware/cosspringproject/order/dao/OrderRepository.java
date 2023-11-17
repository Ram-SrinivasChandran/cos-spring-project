package net.breezeware.cosspringproject.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.cosspringproject.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrderByStatus(String status);
}
