package net.breezeware.cosspringproject.order.service.api;

import java.util.List;

import net.breezeware.cosspringproject.order.dto.FoodItemDto;
import net.breezeware.cosspringproject.order.entity.Order;
import net.breezeware.cosspringproject.order.entity.OrderItem;

/**
 * Service interface for managing order items.
 */
public interface OrderItemService {

    /**
     * Creates a new order item.
     * @param  orderItem The order item to create.
     * @return           The created order item.
     */
    OrderItem createOrderItem(OrderItem orderItem);

    /**
     * Retrieves a list of order items associated with a specific order.
     * @param  order The order for which to retrieve order items.
     * @return       A list of order items associated with the order.
     */
    List<OrderItem> findByOrder(Order order);

    /**
     * Deletes an order item by its unique identifier.
     * @param orderId The unique identifier of the order item to delete.
     */
    void deleteOrderItemById(long orderId);

    /**
     * Adds food items to an order and calculates the total cost.
     * @param  order        The order to which food items are added.
     * @param  foodItemDtos A list of food items with their required quantities.
     * @return              The total cost of the order after adding the food items.
     */
    double addOrderItems(Order order, List<FoodItemDto> foodItemDtos);
}
