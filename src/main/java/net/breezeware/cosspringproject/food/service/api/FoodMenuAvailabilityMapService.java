package net.breezeware.cosspringproject.food.service.api;

import java.util.List;

import net.breezeware.cosspringproject.food.entity.Availability;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuAvailabilityMap;

public interface FoodMenuAvailabilityMapService {
    List<FoodMenuAvailabilityMap> findAll();

    FoodMenuAvailabilityMap findById(long id);

    FoodMenuAvailabilityMap save(FoodMenuAvailabilityMap foodMenuAvailabilityMap);

    void delete(FoodMenuAvailabilityMap foodMenuAvailabilityMap);

    void deleteById(long id);

    void update(Long id, FoodMenuAvailabilityMap foodMenuAvailabilityMap);

    List<FoodMenuAvailabilityMap> getFoodMenuAvailabilityMapByFoodMenu(FoodMenu foodMenu);

    List<FoodMenuAvailabilityMap> getFoodMenuAvailabilityMapByAvailability(Availability availability);
}
