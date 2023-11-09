package net.breezeware.cosspringproject.order.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.service.api.FoodItemService;
import net.breezeware.cosspringproject.order.dao.OrderItemRepository;
import net.breezeware.cosspringproject.order.dto.FoodItemDto;
import net.breezeware.cosspringproject.order.entity.Order;
import net.breezeware.cosspringproject.order.entity.OrderItem;
import net.breezeware.cosspringproject.order.service.api.OrderItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final FoodItemService foodItemService;

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        log.info("Entering createOrderItem()");
        OrderItem savedOrder = orderItemRepository.save(orderItem);
        log.info("Leaving createOrderItem()");
        return savedOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderItem> findByOrder(Order order) {
        log.info("Entering findByOrder()");
        List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
        log.info("Leaving findByOrder()");
        return orderItems;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOrderItemById(long orderId) {
        log.info("Entering deleteOrderItemById()");
        orderItemRepository.deleteById(orderId);
        log.info("Leaving deleteOrderItemById()");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double addOrderItems(Order order, List<FoodItemDto> foodItemDtos) {
        log.info("Entering addOrderItems()");
        double totalCostOfTheOrder = 0;
        for (var foodItemDto : foodItemDtos) {
            FoodItem foodItem = foodItemDto.getFoodItem();
            FoodItem retainedFoodItem = foodItemService.findFoodItemById(foodItem.getId());
            if (retainedFoodItem.getQuantity() < foodItemDto.getRequiredQuantity()) {
                throw new CustomException("The FoodItem Quantity is Unavailable", HttpStatus.BAD_REQUEST);
            } else {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setFoodItem(foodItem);
                orderItem.setQuantity(foodItemDto.getRequiredQuantity());
                orderItem.setCost(foodItem.getCost() * foodItemDto.getRequiredQuantity());
                orderItem.setCreatedOn(Instant.now());
                orderItem.setModifiedOn(Instant.now());
                OrderItem createdOrderItem = createOrderItem(orderItem);
                totalCostOfTheOrder += createdOrderItem.getCost();
            }

        }
        log.info("Leaving addOrderItems()");
        return totalCostOfTheOrder;
    }
}
