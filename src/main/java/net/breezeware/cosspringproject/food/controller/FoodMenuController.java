package net.breezeware.cosspringproject.food.controller;

import lombok.RequiredArgsConstructor;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.service.api.FoodMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/foodMenus")
@RequiredArgsConstructor
public class FoodMenuController {
    private final FoodMenuService foodMenuService;

    @GetMapping
    public List<FoodMenu> getFoodItems(){
        return foodMenuService.findAll();
    }
    @GetMapping("/{id}")
    public FoodMenu getFoodItemById(@PathVariable Long id){
        return foodMenuService.findById(id);
    }
}
