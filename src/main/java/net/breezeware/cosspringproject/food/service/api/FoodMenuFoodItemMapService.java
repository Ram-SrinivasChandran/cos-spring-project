package net.breezeware.cosspringproject.food.service.api;

import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuFoodItemMap;

import java.util.List;

public interface FoodMenuFoodItemMapService {
    List<FoodMenuFoodItemMap> findAll();
    FoodMenuFoodItemMap findById(long id);
    FoodMenuFoodItemMap save(FoodMenuFoodItemMap foodMenuFoodItemMap);
    void delete(FoodMenuFoodItemMap foodMenuFoodItemMap);
    void deleteById(long id);
    void update(Long id,FoodMenuFoodItemMap foodMenuFoodItemMap);
}
