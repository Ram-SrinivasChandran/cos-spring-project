package net.breezeware.cosspringproject.order.dto;

import net.breezeware.cosspringproject.food.entity.FoodItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodItemDto {
    private FoodItem foodItem;
    private int requiredQuantity;
}
