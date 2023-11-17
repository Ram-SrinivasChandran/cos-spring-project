package net.breezeware.cosspringproject.food.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.breezeware.cosspringproject.food.entity.FoodMenu;

@Repository
public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {
    boolean existsByNameAndType(String name, String type);
}
