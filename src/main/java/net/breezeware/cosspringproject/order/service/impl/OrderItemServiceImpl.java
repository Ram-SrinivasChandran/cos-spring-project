package net.breezeware.cosspringproject.order.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.service.api.FoodItemService;
import net.breezeware.cosspringproject.order.dao.OrderItemRepository;
import net.breezeware.cosspringproject.order.dto.FoodItemDto;
import net.breezeware.cosspringproject.order.entity.Order;
import net.breezeware.cosspringproject.order.entity.OrderItem;
import net.breezeware.cosspringproject.order.service.api.OrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final FoodItemService foodItemService;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);

    }
    @Override
    public List<OrderItem> findByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }

    @Override
    public void deleteOrderItemById(long id) {
        orderItemRepository.deleteById(id);
    }

    @Override
    public double addOrderItems(Order order,List<FoodItemDto> foodItemDtos) {
        double totalCostOfTheOrder=0;
        for(var foodItemDto:foodItemDtos){
            FoodItem foodItem=foodItemDto.getFoodItem();
            FoodItem retainedFoodItem = foodItemService.findById(foodItem.getId());
            if(retainedFoodItem.getQuantity()<foodItemDto.getRequiredQuantity()){
                throw new CustomException("The FoodItem Quantity is Unavailable", HttpStatus.BAD_REQUEST);
            }else {
                OrderItem orderItem=new OrderItem();
                orderItem.setOrder(order);
                orderItem.setFoodItem(foodItem);
                orderItem.setQuantity(foodItemDto.getRequiredQuantity());
                orderItem.setCost(foodItem.getCost()*foodItemDto.getRequiredQuantity());
                orderItem.setCreatedOn(Instant.now());
                orderItem.setModifiedOn(Instant.now());
                OrderItem createdOrderItem = createOrderItem(orderItem);
                totalCostOfTheOrder+=createdOrderItem.getCost();
            }
        }
        return totalCostOfTheOrder;
    }
}
