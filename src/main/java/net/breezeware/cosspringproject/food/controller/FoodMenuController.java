package net.breezeware.cosspringproject.food.controller;

import lombok.RequiredArgsConstructor;
import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.service.api.FoodMenuService;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createFoodMenu(@RequestBody FoodMenuDto foodMenuDto){
        foodMenuService.save(foodMenuDto);
    }
    @PutMapping("/{id}")
    public void updateFoodMenu(@PathVariable Long id,@RequestBody FoodMenuDto foodMenuDto){
        foodMenuService.update(id,foodMenuDto);
    }
    @DeleteMapping("/{id}")
    public void deleteFoodMenu(@PathVariable Long id){
        foodMenuService.deleteById(id);
    }
}
