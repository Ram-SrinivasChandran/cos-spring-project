package net.breezeware.cosspringproject.food.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.service.api.FoodMenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/foodMenus")
@RequiredArgsConstructor
public class FoodMenuController {
    private final FoodMenuService foodMenuService;

    @GetMapping
    public List<FoodMenu> getFoodMenus() {
        return foodMenuService.findAllFoodMenu();
    }

    @GetMapping("/{food-menu-id}")
    public FoodMenu getFoodMenuById(@PathVariable(name = "food-menu-id",required = true) Long foodMenuId) {
        return foodMenuService.findFoodMenuById(foodMenuId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createFoodMenu(@RequestBody FoodMenuDto foodMenuDto) {
        foodMenuService.saveFoodMenu(foodMenuDto);
    }

    @PutMapping("/{food-menu-id}")
    public void updateFoodMenu(@PathVariable(name = "food-menu-id",required = true) Long foodMenuId, @RequestBody FoodMenuDto foodMenuDto) {
        foodMenuService.updateFoodMenu(foodMenuId, foodMenuDto);
    }

    @DeleteMapping("/{food-menu-id}")
    public void deleteFoodMenu(@PathVariable(name = "food-menu-id",required = true) Long foodMenuId) {
        foodMenuService.deleteFoodMenuById(foodMenuId);
    }
}
