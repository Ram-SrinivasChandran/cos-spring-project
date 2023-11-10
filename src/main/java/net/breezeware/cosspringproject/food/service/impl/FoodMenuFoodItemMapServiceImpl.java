package net.breezeware.cosspringproject.food.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.food.dao.FoodMenuFoodItemMapRepository;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuFoodItemMap;
import net.breezeware.cosspringproject.food.service.api.FoodMenuFoodItemMapService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodMenuFoodItemMapServiceImpl implements FoodMenuFoodItemMapService {

    private final FoodMenuFoodItemMapRepository foodMenuFoodItemMapRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FoodMenuFoodItemMap> findAllFoodMenuFoodItemMap() {
        log.info("Entering findAllFoodMenuFoodItemMap()");
        List<FoodMenuFoodItemMap> foodMenuFoodItemMaps = foodMenuFoodItemMapRepository.findAll();
        log.info("Leaving findAllFoodMenuFoodItemMap()");
        return foodMenuFoodItemMaps;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FoodMenuFoodItemMap findFoodMenuFoodItemMapById(long foodMenuFoodItemMapId) {
        log.info("Entering findFoodMenuFoodItemMapById()");
        if (foodMenuFoodItemMapId <= 0) {
            throw new CustomException("The FoodMenuFoodItemMap Id should be Greater than Zero.",
                    HttpStatus.BAD_REQUEST);
        }

        log.info("Leaving findFoodMenuFoodItemMapById()");
        return foodMenuFoodItemMapRepository.findById(foodMenuFoodItemMapId)
                .orElseThrow(() -> new CustomException("The FoodMenuFoodItemMap not Found", HttpStatus.NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public FoodMenuFoodItemMap saveFoodMenuFoodItemMap(FoodMenuFoodItemMap foodMenuFoodItemMap) {
        log.info("Entering saveFoodMenuFoodItemMap()");
        FoodMenuFoodItemMap savedFoodMenuFoodItemMap = foodMenuFoodItemMapRepository.save(foodMenuFoodItemMap);
        log.info("Leaving saveFoodMenuFoodItemMap()");
        return savedFoodMenuFoodItemMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FoodMenuFoodItemMap> getFoodMenuFoodItemMapByFoodMenu(FoodMenu foodMenu) {
        log.info("Entering getFoodMenuFoodItemMapByFoodMenu()");
        List<FoodMenuFoodItemMap> foodMenuFoodItemMapByFoodMenus =
                foodMenuFoodItemMapRepository.getFoodMenuFoodItemMapByFoodMenu(foodMenu);
        log.info("Entering getFoodMenuFoodItemMapByFoodMenu()");
        return foodMenuFoodItemMapByFoodMenus;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteFoodMenuFoodItemMap(FoodMenuFoodItemMap foodMenuFoodItemMap) {
        log.info("Entering deleteFoodMenuFoodItemMap()");
        foodMenuFoodItemMapRepository.delete(foodMenuFoodItemMap);
        log.info("Leaving deleteFoodMenuFoodItemMap()");
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteFoodMenuFoodItemMapById(long foodMenuFoodItemMapId) {
        log.info("Entering deleteFoodMenuFoodItemMapId()");
        foodMenuFoodItemMapRepository.deleteById(foodMenuFoodItemMapId);
        log.info("Leaving deleteFoodMenuFoodItemMapById()");
    }

}
