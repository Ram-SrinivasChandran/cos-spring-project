package net.breezeware.cosspringproject.food.service.api;

import net.breezeware.cosspringproject.food.entity.FoodItem;

public interface FoodItemService extends GenericFoodService<FoodItem,Long>{
    void update(Long id,FoodItem foodItem);
}
