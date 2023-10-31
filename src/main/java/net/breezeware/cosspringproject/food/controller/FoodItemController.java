package net.breezeware.cosspringproject.food.controller;

import lombok.RequiredArgsConstructor;
import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.service.api.FoodItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foodItems")
@RequiredArgsConstructor
public class FoodItemController {

    private final FoodItemService foodItemService;

    @GetMapping
    public List<FoodItem> getFoodItems(){
        return foodItemService.findAll();
    }
    @GetMapping("/{id}")
    public FoodItem getFoodItemById(@PathVariable Long id){
        return foodItemService.findById(id);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void saveFoodItem(@RequestBody FoodItem foodItem){
        foodItemService.save(foodItem);
    }
    @PutMapping("/{id}")
    public void updateFoodItem(@PathVariable Long id,@RequestBody FoodItem foodItem){
        foodItemService.update(id,foodItem);
    }

    @DeleteMapping("/{id}")
    public void deleteFoodItem(@PathVariable Long id){
        foodItemService.deleteById(id);
    }
}
