package net.breezeware.cosspringproject.food.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.breezeware.cosspringproject.food.entity.Availability;
import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.entity.FoodMenu;

import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodMenuDto {
    private FoodMenu foodMenu;
    private List<FoodItem> foodItems;
    private List<Availability>availabilityList;
}
