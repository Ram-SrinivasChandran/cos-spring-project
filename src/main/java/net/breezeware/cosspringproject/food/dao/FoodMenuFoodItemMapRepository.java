package net.breezeware.cosspringproject.food.dao;

import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuFoodItemMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodMenuFoodItemMapRepository extends JpaRepository<FoodMenuFoodItemMap,Long> {
    List<FoodMenuFoodItemMap> getFoodMenuFoodItemMapByFoodMenu(FoodMenu foodMenu);
}
