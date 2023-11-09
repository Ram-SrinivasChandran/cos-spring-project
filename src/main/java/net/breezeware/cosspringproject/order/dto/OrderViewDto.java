package net.breezeware.cosspringproject.order.dto;

import java.util.List;

import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.order.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing an order view with associated food
 * items.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderViewDto {
    /**
     * The order associated with this order view.
     */
    private Order order;

    /**
     * The list of food items associated with the order view.
     */
    private List<FoodItem> foodItems;
}
