package net.breezeware.cosspringproject.food.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import net.breezeware.cosspringproject.food.entity.FoodMenu;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {
    boolean existsByNameAndType(String name, String type);
}
