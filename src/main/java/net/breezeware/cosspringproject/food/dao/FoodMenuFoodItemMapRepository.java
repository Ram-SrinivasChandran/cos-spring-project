package net.breezeware.cosspringproject.food.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuFoodItemMap;

@Repository
public interface FoodMenuFoodItemMapRepository extends JpaRepository<FoodMenuFoodItemMap, Long> {
    List<FoodMenuFoodItemMap> getFoodMenuFoodItemMapByFoodMenu(FoodMenu foodMenu);
}
