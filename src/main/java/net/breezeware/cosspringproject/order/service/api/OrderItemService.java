package net.breezeware.cosspringproject.order.service.api;

import net.breezeware.cosspringproject.order.entity.Order;
import net.breezeware.cosspringproject.order.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItem createOrderItem(OrderItem orderItem);
    List<OrderItem> findByOrder(Order order);
}
