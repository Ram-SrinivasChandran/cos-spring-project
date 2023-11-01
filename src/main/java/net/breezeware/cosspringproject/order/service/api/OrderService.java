package net.breezeware.cosspringproject.order.service.api;

import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.FoodMenu;

import java.util.List;

public interface OrderService {
    List<FoodMenuDto> viewFoodMenus();
}
