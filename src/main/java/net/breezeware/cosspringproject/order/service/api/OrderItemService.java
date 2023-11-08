package net.breezeware.cosspringproject.order.service.api;

import java.util.List;

import net.breezeware.cosspringproject.order.dto.FoodItemDto;
import net.breezeware.cosspringproject.order.entity.Order;
import net.breezeware.cosspringproject.order.entity.OrderItem;

public interface OrderItemService {
    OrderItem createOrderItem(OrderItem orderItem);

    List<OrderItem> findByOrder(Order order);

    void deleteOrderItemById(long id);

    double addOrderItems(Order order, List<FoodItemDto> foodItemDtos);
}
