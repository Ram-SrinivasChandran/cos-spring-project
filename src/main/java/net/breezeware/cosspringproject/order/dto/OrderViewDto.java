package net.breezeware.cosspringproject.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.order.entity.Order;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderViewDto {
    private Order order;
    private List<FoodItem> foodItems;
}
