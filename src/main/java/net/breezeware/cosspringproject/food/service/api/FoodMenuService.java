package net.breezeware.cosspringproject.food.service.api;

import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.entity.FoodMenu;

import java.util.List;

public interface FoodMenuService {
    List<FoodMenu> findAll();
    FoodMenu findById(long id);
    FoodMenu save(FoodMenuDto foodMenuDto);
    void delete(FoodMenu foodMenu);
    void deleteById(long id);
    void update(Long id,FoodMenu foodMenu);
}
