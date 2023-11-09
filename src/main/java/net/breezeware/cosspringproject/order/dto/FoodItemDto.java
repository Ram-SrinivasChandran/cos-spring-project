package net.breezeware.cosspringproject.order.dto;

import net.breezeware.cosspringproject.food.entity.FoodItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing a food item with its required
 * quantity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodItemDto {
    /**
     * The food item associated with this DTO.
     */
    private FoodItem foodItem;

    /**
     * The required quantity of the food item.
     */
    private int requiredQuantity;
}
