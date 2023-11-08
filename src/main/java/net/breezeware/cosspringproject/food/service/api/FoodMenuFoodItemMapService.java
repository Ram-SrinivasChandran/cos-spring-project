package net.breezeware.cosspringproject.food.service.api;

import java.util.List;

import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuFoodItemMap;

public interface FoodMenuFoodItemMapService {
    List<FoodMenuFoodItemMap> findAll();

    FoodMenuFoodItemMap findById(long id);

    FoodMenuFoodItemMap save(FoodMenuFoodItemMap foodMenuFoodItemMap);

    List<FoodMenuFoodItemMap> getFoodMenuFoodItemMapByFoodMenu(FoodMenu foodMenu);

    void delete(FoodMenuFoodItemMap foodMenuFoodItemMap);

    void deleteById(long id);

    void update(Long id, FoodMenuFoodItemMap foodMenuFoodItemMap);
}
