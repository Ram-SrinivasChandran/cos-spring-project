package net.breezeware.cosspringproject.food.service.api;

import java.util.List;

import net.breezeware.cosspringproject.food.entity.FoodItem;

public interface FoodItemService {
    List<FoodItem> findAll();

    FoodItem findById(long id);

    FoodItem save(FoodItem object);

    void delete(FoodItem object);

    void deleteById(long id);

    void update(Long id, FoodItem foodItem);
}
