package net.breezeware.cosspringproject.food.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.cosspringproject.food.entity.Availability;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuAvailabilityMap;

@Repository
public interface FoodMenuAvailabilityMapRepository extends JpaRepository<FoodMenuAvailabilityMap, Long> {
    List<FoodMenuAvailabilityMap> getFoodMenuAvailabilityMapByFoodMenu(FoodMenu foodMenu);

    List<FoodMenuAvailabilityMap> getFoodMenuAvailabilityMapByAvailability(Availability availability);
}
