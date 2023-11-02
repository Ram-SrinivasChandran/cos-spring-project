package net.breezeware.cosspringproject.order.service.api;

import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.order.dto.OrderDto;
import net.breezeware.cosspringproject.order.entity.Order;

import java.util.List;

public interface OrderService {
    List<FoodMenuDto> viewFoodMenus();
    Order createOrder(OrderDto orderDto);
}
