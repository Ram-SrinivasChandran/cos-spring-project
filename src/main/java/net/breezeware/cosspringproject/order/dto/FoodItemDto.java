package net.breezeware.cosspringproject.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.cosspringproject.food.entity.FoodItem;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodItemDto {
    private FoodItem foodItem;
    private int requiredQuantity;
}
