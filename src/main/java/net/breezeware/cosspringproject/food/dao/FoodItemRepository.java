package net.breezeware.cosspringproject.food.dao;

import net.breezeware.cosspringproject.food.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItem,Long> {
}
