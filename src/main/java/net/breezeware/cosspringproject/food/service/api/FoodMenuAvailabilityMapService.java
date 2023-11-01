package net.breezeware.cosspringproject.food.service.api;

import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuAvailabilityMap;

import java.util.List;

public interface FoodMenuAvailabilityMapService {
    List<FoodMenuAvailabilityMap> findAll();
    FoodMenuAvailabilityMap findById(long id);
    FoodMenuAvailabilityMap save(FoodMenuAvailabilityMap foodMenuAvailabilityMap);
    void delete(FoodMenuAvailabilityMap foodMenuAvailabilityMap);
    void deleteById(long id);
    void update(Long id,FoodMenuAvailabilityMap foodMenuAvailabilityMap);
    List<FoodMenuAvailabilityMap> getFoodMenuAvailabilityMapByFoodMenu(FoodMenu foodMenu);
}
