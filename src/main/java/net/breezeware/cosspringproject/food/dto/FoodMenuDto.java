package net.breezeware.cosspringproject.food.dto;

import lombok.Builder;
import lombok.Data;
import net.breezeware.cosspringproject.food.entity.Availability;
import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.entity.FoodMenu;

import java.util.List;
@Builder
@Data
public class FoodMenuDto {
    private FoodMenu foodMenu;
    private List<FoodItem> foodItems;
    private List<Availability>availabilityList;
}
