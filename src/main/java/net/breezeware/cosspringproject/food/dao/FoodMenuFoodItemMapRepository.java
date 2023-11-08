package net.breezeware.cosspringproject.food.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuFoodItemMap;

public interface FoodMenuFoodItemMapRepository extends JpaRepository<FoodMenuFoodItemMap, Long> {
    List<FoodMenuFoodItemMap> getFoodMenuFoodItemMapByFoodMenu(FoodMenu foodMenu);
}
