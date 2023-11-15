package net.breezeware.cosspringproject.food.dto;

import java.util.List;

import net.breezeware.cosspringproject.food.entity.Availability;
import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.entity.FoodMenu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The `FoodMenuDto` class is a Data Transfer Object (DTO) used to transfer data
 * related to a Food Menu. It encapsulates a FoodMenu entity, a list of FoodItem
 * entities, and a list of Availability entities.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Food Menu Dto")
public class FoodMenuDto {
    /**
     * The FoodMenu entity associated with this DTO.
     */
    private FoodMenu foodMenu;

    /**
     * A list of FoodItem entities included in the Food Menu.
     */
    private List<FoodItem> foodItems;

    /**
     * A list of Availability entities indicating the availability of the Food Menu.
     */
    private List<Availability> availabilityList;
}
