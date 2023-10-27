package net.breezeware.cosspringproject.food.dao;

import net.breezeware.cosspringproject.food.entity.FoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodMenuRepository extends JpaRepository<FoodMenu,Long> {
}
