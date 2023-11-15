package net.breezeware.cosspringproject.order.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import net.breezeware.cosspringproject.order.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) representing an order with associated food item
 * DTOs.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Order Dto")
public class OrderDto {
    /**
     * The order associated with this DTO.
     */
    private Order order;

    /**
     * The list of food item DTOs associated with the order.
     */
    private List<FoodItemDto> foodItemDtos;
}
