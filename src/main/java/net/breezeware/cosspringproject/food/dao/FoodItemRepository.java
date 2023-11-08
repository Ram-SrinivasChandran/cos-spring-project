package net.breezeware.cosspringproject.food.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import net.breezeware.cosspringproject.food.entity.FoodItem;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
}
