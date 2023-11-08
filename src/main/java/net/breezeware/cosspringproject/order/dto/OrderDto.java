package net.breezeware.cosspringproject.order.dto;

import java.util.List;

import net.breezeware.cosspringproject.order.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Order order;
    private List<FoodItemDto> foodItemDtos;
}
