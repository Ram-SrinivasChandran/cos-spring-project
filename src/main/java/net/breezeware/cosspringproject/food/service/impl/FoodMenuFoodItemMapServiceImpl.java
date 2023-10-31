package net.breezeware.cosspringproject.food.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.breezeware.cosspringproject.exception.ValidationException;
import net.breezeware.cosspringproject.food.dao.FoodMenuFoodItemMapRepository;
import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuFoodItemMap;
import net.breezeware.cosspringproject.food.service.api.FoodMenuFoodItemMapService;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

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
    public void delete(FoodMenuFoodItemMap foodMenuFoodItemMap) {

    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public void update(Long id, FoodMenuFoodItemMap foodMenuFoodItemMap) {

    }
}
