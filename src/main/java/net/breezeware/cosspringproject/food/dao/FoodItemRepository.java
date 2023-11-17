package net.breezeware.cosspringproject.food.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.cosspringproject.food.entity.FoodItem;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
}
