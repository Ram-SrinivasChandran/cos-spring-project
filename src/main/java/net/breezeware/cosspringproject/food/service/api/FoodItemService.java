package net.breezeware.cosspringproject.food.service.api;

import net.breezeware.cosspringproject.food.entity.FoodItem;

import java.util.List;

public interface FoodItemService {
    List<FoodItem> findAll();
    FoodItem findById(long id);
    FoodItem save(FoodItem object);
    void delete(FoodItem object);
    void deleteById(long id);
    void update(Long id,FoodItem foodItem);
}
