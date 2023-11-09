package net.breezeware.cosspringproject.order.service.api;

import java.util.List;

import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.order.dto.FoodItemDto;
import net.breezeware.cosspringproject.order.dto.OrderDto;
import net.breezeware.cosspringproject.order.dto.OrderViewDto;
import net.breezeware.cosspringproject.order.dto.PlaceOrderDto;
import net.breezeware.cosspringproject.order.entity.Order;
import net.breezeware.cosspringproject.user.entity.UserAddressMap;

/**
 * Service interface for managing orders and related operations.
 */
public interface OrderService {

    /**
     * Retrieves the available food menus for the current day.
     * @return List of FoodMenuDto containing available food menus, their items, and
     *         availabilities.
     */
    List<FoodMenuDto> retrieveAvailableFoodMenusForToday();

    /**
     * Create a new order based on the provided order details.
     * @param  orderDto The order details.
     * @return          The created order.
     */
    Order createOrder(OrderDto orderDto);

    /**
     * View the details of a specific order.
     * @param  orderId The unique identifier of the order to view.
     * @return         The order details.
     */
    OrderViewDto viewOrder(long orderId);

    /**
     * Find an order by its unique identifier.
     * @param  orderId The unique identifier of the order to find.
     * @return         The found order, or null if not found.
     */
    Order findOrderById(long orderId);

    /**
     * Update the food items in an existing order and recalculate the total cost.
     * @param orderId      The unique identifier of the order to update.
     * @param foodItemDtos A list of food items with their required quantities.
     */
    void updateOrder(long orderId, List<FoodItemDto> foodItemDtos);

    /**
     * Create a new user address mapping for placing orders.
     * @param  userAddressMap The user address mapping to create.
     * @return                The created user address mapping.
     */
    UserAddressMap createAddress(UserAddressMap userAddressMap);

    /**
     * Place an order with specified details and items.
     * @param  orderId       The unique identifier of the order.
     * @param  placeOrderDto The order placement details.
     * @return               The details of the placed order.
     */
    OrderViewDto placeOrder(long orderId, PlaceOrderDto placeOrderDto);

    /**
     * Cancel an order with the specified unique identifier.
     * @param orderId The unique identifier of the order to cancel.
     */
    void cancelOrder(long orderId);

    /**
     * View the list of active orders for a user.
     * @param  orderId The unique identifier of the user.
     * @return         A list of active orders.
     */
    List<OrderViewDto> viewActiveOrders(long orderId);

    /**
     * View a received order by a user.
     * @param  userId  The unique identifier of the user.
     * @param  orderId The unique identifier of the received order.
     * @return         The details of the received order.
     */
    OrderViewDto viewReceivedOrder(long userId, long orderId);

    /**
     * Change the status of an order to "Waiting for Delivery".
     * @param userId  The unique identifier of the user.
     * @param orderId The unique identifier of the order.
     */
    void changeStatusToWaitingForDelivery(long userId, long orderId);

    /**
     * Change the status of an order to "Pending Delivery".
     * @param userId  The unique identifier of the user.
     * @param orderId The unique identifier of the order.
     */
    void changeStatusToPendingDelivery(long userId, long orderId);

    /**
     * Change the status of an order to "Order Delivered".
     * @param userId  The unique identifier of the user.
     * @param orderId The unique identifier of the order.
     */
    void changeStatusToOrderDelivered(long userId, long orderId);

    /**
     * View the list of cancelled orders for a user.
     * @param  orderId The unique identifier of the user.
     * @return         A list of cancelled orders.
     */
    List<OrderViewDto> viewCancelledOrders(long orderId);

    /**
     * View a specific cancelled order by a user.
     * @param  userId  The unique identifier of the user.
     * @param  orderId The unique identifier of the cancelled order.
     * @return         The details of the cancelled order.
     */
    OrderViewDto viewCancelledOrder(long userId, long orderId);

    /**
     * View the list of completed orders for a user.
     * @param  orderId The unique identifier of the user.
     * @return         A list of completed orders.
     */
    List<OrderViewDto> viewCompletedOrders(long orderId);

    /**
     * View a specific completed order by a user.
     * @param  userId  The unique identifier of the user.
     * @param  orderId The unique identifier of the completed order.
     * @return         The details of the completed order.
     */
    OrderViewDto viewCompletedOrder(long userId, long orderId);
}
