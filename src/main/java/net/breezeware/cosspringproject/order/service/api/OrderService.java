package net.breezeware.cosspringproject.order.service.api;

import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.order.dto.FoodItemDto;
import net.breezeware.cosspringproject.order.dto.OrderDto;
import net.breezeware.cosspringproject.order.dto.OrderViewDto;
import net.breezeware.cosspringproject.order.dto.PlaceOrderDto;
import net.breezeware.cosspringproject.order.entity.Order;
import net.breezeware.cosspringproject.user.entity.UserAddressMap;

import java.util.List;

public interface OrderService {
    List<FoodMenuDto> viewFoodMenus();
    Order createOrder(OrderDto orderDto);
    OrderViewDto viewOrder(long id);
    Order findById(long id);
    void updateOrder(long id,List<FoodItemDto>foodItemDtos);
    UserAddressMap createAddress(UserAddressMap userAddressMap);
    OrderViewDto placeOrder(long id, PlaceOrderDto placeOrderDto);
    void cancelOrder(long id);
    List<OrderViewDto> viewActiveOrders(long id);
    OrderViewDto viewReceivedOrder(long userId,long orderId);
    void changeStatusToWaitingForDelivery(long userId, long orderId);
    void changeStatusToPendingDelivery(long userId, long orderId);
    void changeStatusToOrderDelivered(long userId, long orderId);
}
