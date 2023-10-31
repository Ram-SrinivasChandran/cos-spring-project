package net.breezeware.cosspringproject.food.controller;

import lombok.RequiredArgsConstructor;
import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.service.api.FoodMenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foodMenus")
@RequiredArgsConstructor
public class FoodMenuController {
    private final FoodMenuService foodMenuService;

    @GetMapping
    public List<FoodMenu> getFoodMenus(){
        return foodMenuService.findAll();
    }
    @GetMapping("/{id}")
    public FoodMenu getFoodMenuById(@PathVariable Long id){
        return foodMenuService.findById(id);
    }
    @PostMapping
    public FoodMenu createFoodMenu(@RequestBody FoodMenuDto foodMenuDto){
        return foodMenuService.save(foodMenuDto);
    }
}
