package net.breezeware.cosspringproject.food.service.impl;

import java.util.List;

import javax.validation.Validator;

import org.springframework.stereotype.Service;

import net.breezeware.cosspringproject.food.dao.FoodMenuFoodItemMapRepository;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuFoodItemMap;
import net.breezeware.cosspringproject.food.service.api.FoodMenuFoodItemMapService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodMenuFoodItemMapServiceImpl implements FoodMenuFoodItemMapService {

    private final FoodMenuFoodItemMapRepository foodMenuFoodItemMapRepository;
    private final Validator fieldValidator;

    @Override
    public List<FoodMenuFoodItemMap> findAll() {
        return null;
    }

    @Override
    public FoodMenuFoodItemMap findById(long id) {
        return null;
    }

    @Override
    public FoodMenuFoodItemMap save(FoodMenuFoodItemMap foodMenuFoodItemMap) {
        return foodMenuFoodItemMapRepository.save(foodMenuFoodItemMap);
    }

    @Override
    public List<FoodMenuFoodItemMap> getFoodMenuFoodItemMapByFoodMenu(FoodMenu foodMenu) {
        return foodMenuFoodItemMapRepository.getFoodMenuFoodItemMapByFoodMenu(foodMenu);
    }

    @Override
    public void delete(FoodMenuFoodItemMap foodMenuFoodItemMap) {

    }

    @Override
    public void deleteById(long id) {
        foodMenuFoodItemMapRepository.deleteById(id);
    }

    @Override
    public void update(Long id, FoodMenuFoodItemMap foodMenuFoodItemMap) {

    }
}
