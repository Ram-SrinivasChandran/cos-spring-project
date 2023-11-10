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

import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.service.api.FoodMenuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/food-menus")
@RequiredArgsConstructor
@Slf4j
public class FoodMenuController {
    private final FoodMenuService foodMenuService;

    @GetMapping
    public List<FoodMenu> retrieveFoodMenus() {
        log.info("Entering retrieveFoodMenus()");
        List<FoodMenu> foodMenus = foodMenuService.findAllFoodMenu();
        log.info("Leaving retrieveFoodMenus()");
        return foodMenus;
    }

    @GetMapping("/{food-menu-id}")
    public FoodMenu retrieveFoodMenuById(@PathVariable(name = "food-menu-id",required = true) Long foodMenuId) {
        log.info("Entering retrieveFoodMenuById()");
        FoodMenu foodMenu = foodMenuService.findFoodMenuById(foodMenuId);
        log.info("Leaving retrieveFoodMenuById()");
        return foodMenu;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createFoodMenu(@RequestBody FoodMenuDto foodMenuDto) {
        log.info("Entering createFoodMenu()");
        foodMenuService.saveFoodMenu(foodMenuDto);
        log.info("Leaving createFoodMenu()");
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{food-menu-id}")
    public void updateFoodMenu(@PathVariable(name = "food-menu-id",required = true) Long foodMenuId, @RequestBody FoodMenuDto foodMenuDto) {
        log.info("Entering updateFoodMenu()");
        foodMenuService.updateFoodMenu(foodMenuId, foodMenuDto);
        log.info("Leaving updateFoodMenu()");
    }

    @DeleteMapping("/{food-menu-id}")
    public void deleteFoodMenu(@PathVariable(name = "food-menu-id",required = true) Long foodMenuId) {
        log.info("Entering deleteFoodMenu()");
        foodMenuService.deleteFoodMenuById(foodMenuId);
        log.info("Leaving deleteFoodMenu()");
    }
}
