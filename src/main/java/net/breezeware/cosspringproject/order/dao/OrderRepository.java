package net.breezeware.cosspringproject.order.dao;

import net.breezeware.cosspringproject.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
