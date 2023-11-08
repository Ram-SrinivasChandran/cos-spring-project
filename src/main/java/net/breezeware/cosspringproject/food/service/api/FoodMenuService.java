package net.breezeware.cosspringproject.food.service.api;

import java.util.List;

import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.FoodMenu;

public interface FoodMenuService {
    List<FoodMenu> findAll();

    FoodMenu findById(long id);

    FoodMenu save(FoodMenuDto foodMenuDto);

    void delete(FoodMenu foodMenu);

    void deleteById(long id);

    void update(Long id, FoodMenuDto foodMenuDto);
}
