package net.breezeware.cosspringproject.food.dto;

import java.util.List;

import net.breezeware.cosspringproject.food.entity.Availability;
import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.entity.FoodMenu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodMenuDto {
    private FoodMenu foodMenu;
    private List<FoodItem> foodItems;
    private List<Availability> availabilityList;
}
