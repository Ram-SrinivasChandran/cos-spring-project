package net.breezeware.cosspringproject.food.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
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

import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.service.api.FoodItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/food-items")
@RequiredArgsConstructor
@Slf4j
public class FoodItemController {

    private final FoodItemService foodItemService;

    @GetMapping
    public List<FoodItem> retrieveFoodItems() {
        log.info("Entering retrieveFoodItems()");
        List<FoodItem> foodItems = foodItemService.findAllFoodItems();
        log.info("Leaving retrieveFoodItems()");
        return foodItems;
    }

    @GetMapping("/{food-item-id}")
    public FoodItem retrieveFoodItemById(@PathVariable(name = "food-item-id",required = true) Long foodItemId) {
        log.info("Entering retrieveFoodItemById()");
        FoodItem foodItem = foodItemService.findFoodItemById(foodItemId);
        log.info("Leaving retrieveFoodItemById()");
        return foodItem;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void saveFoodItem(@RequestBody FoodItem foodItem) {
        log.info("Entering saveFoodItem()");
        foodItemService.saveFoodItem(foodItem);
        log.info("Leaving saveFoodItem()");
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{food-item-id}")
    public void updateFoodItem(@PathVariable(name = "food-item-id",required = true) Long foodItemId, @RequestBody FoodItem foodItem) {
        log.info("Entering updateFoodItem()");
        foodItemService.updateFoodItem(foodItemId, foodItem);
        log.info("Leaving updateFoodItem()");
    }

    @DeleteMapping("/{food-item-id}")
    public void deleteFoodItem(@PathVariable(name = "food-item-id",required = true) Long foodItemId) {
        log.info("Entering deleteFoodItem()");
        foodItemService.deleteFoodItemById(foodItemId);
        log.info("Leaving deleteFoodItem()");
    }
}

