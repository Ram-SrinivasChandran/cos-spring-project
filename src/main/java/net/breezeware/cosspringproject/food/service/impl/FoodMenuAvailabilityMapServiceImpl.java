package net.breezeware.cosspringproject.food.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.food.dao.FoodMenuAvailabilityMapRepository;
import net.breezeware.cosspringproject.food.entity.Availability;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuAvailabilityMap;
import net.breezeware.cosspringproject.food.service.api.FoodMenuAvailabilityMapService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodMenuAvailabilityMapServiceImpl implements FoodMenuAvailabilityMapService {

    private final FoodMenuAvailabilityMapRepository foodMenuAvailabilityMapRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FoodMenuAvailabilityMap> findAllFoodMenuAvailabilityMap() {
        log.info("Entering findAllFoodMenuAvailabilityMap()");
        List<FoodMenuAvailabilityMap> foodMenuAvailabilities = foodMenuAvailabilityMapRepository.findAll();
        log.info("Leaving findAllFoodMenuAvailabilityMap()");
        return foodMenuAvailabilities;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FoodMenuAvailabilityMap findFoodMenuAvailabilityMapById(long foodMenuAvailabilityMapId) {
        log.info("Entering findFoodMenuAvailabilityMapById()");
        if (foodMenuAvailabilityMapId <= 0) {
            throw new CustomException("The Food Menu Availability Map Id should be Greater than Zero.",
                    HttpStatus.BAD_REQUEST);
        }

        log.info("Leaving findFoodMenuAvailabilityMapById()");
        return foodMenuAvailabilityMapRepository.findById(foodMenuAvailabilityMapId).orElseThrow(
                () -> new CustomException("The Food Menu Availability Map Id not Found", HttpStatus.NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public FoodMenuAvailabilityMap saveFoodMenuAvailabilityMap(FoodMenuAvailabilityMap foodMenuAvailabilityMap) {
        log.info("Entering saveFoodMenuAvailabilityMap()");
        FoodMenuAvailabilityMap savedFoodMenuAvailabilityMap =
                foodMenuAvailabilityMapRepository.save(foodMenuAvailabilityMap);
        log.info("Leaving saveFoodMenuAvailabilityMap()");
        return savedFoodMenuAvailabilityMap;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteFoodMenuAvailabilityMap(FoodMenuAvailabilityMap foodMenuAvailabilityMap) {
        log.info("Entering deleteFoodMenuAvailabilityMap()");
        foodMenuAvailabilityMapRepository.delete(foodMenuAvailabilityMap);
        log.info("Leaving deleteFoodMenuAvailabilityMap()");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FoodMenuAvailabilityMap> getFoodMenuAvailabilityMapByFoodMenu(FoodMenu foodMenu) {
        log.info("Entering getFoodMenuAvailabilityMapByFoodMenu()");
        List<FoodMenuAvailabilityMap> foodMenuAvailabilityMapByFoodMenus =
                foodMenuAvailabilityMapRepository.getFoodMenuAvailabilityMapByFoodMenu(foodMenu);
        log.info("Leaving getFoodMenuAvailabilityMapByFoodMenu()");
        return foodMenuAvailabilityMapByFoodMenus;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteFoodMenuAvailabilityMapById(long foodMenuAvailabilityMapId) {
        log.info("Entering deleteFoodMenuAvailabilityMapById()");
        foodMenuAvailabilityMapRepository.deleteById(foodMenuAvailabilityMapId);
        log.info("Leaving deleteFoodMenuAvailabilityMapById()");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FoodMenuAvailabilityMap> getFoodMenuAvailabilityMapByAvailability(Availability availability) {
        log.info("Entering getFoodMenuAvailabilityMapByAvailability()");
        List<FoodMenuAvailabilityMap> foodMenuAvailabilityMapByAvailabilities =
                foodMenuAvailabilityMapRepository.getFoodMenuAvailabilityMapByAvailability(availability);
        log.info("Leaving getFoodMenuAvailabilityMapByAvailability()");
        return foodMenuAvailabilityMapByAvailabilities;
    }
}
