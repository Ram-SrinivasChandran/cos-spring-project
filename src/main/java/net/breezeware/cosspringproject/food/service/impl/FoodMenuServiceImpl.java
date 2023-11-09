package net.breezeware.cosspringproject.food.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.breezeware.cosspringproject.exception.CustomException;
import net.breezeware.cosspringproject.exception.ValidationException;
import net.breezeware.cosspringproject.food.dao.FoodMenuRepository;
import net.breezeware.cosspringproject.food.dto.FoodMenuDto;
import net.breezeware.cosspringproject.food.entity.Availability;
import net.breezeware.cosspringproject.food.entity.FoodItem;
import net.breezeware.cosspringproject.food.entity.FoodMenu;
import net.breezeware.cosspringproject.food.entity.FoodMenuAvailabilityMap;
import net.breezeware.cosspringproject.food.entity.FoodMenuFoodItemMap;
import net.breezeware.cosspringproject.food.service.api.AvailabilityService;
import net.breezeware.cosspringproject.food.service.api.FoodItemService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuAvailabilityMapService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuFoodItemMapService;
import net.breezeware.cosspringproject.food.service.api.FoodMenuService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodMenuServiceImpl implements FoodMenuService {

    private final FoodMenuRepository foodMenuRepository;
    private final FoodMenuFoodItemMapService foodMenuFoodItemMapService;
    private final FoodMenuAvailabilityMapService foodMenuAvailabilityMapService;
    private final FoodItemService foodItemService;
    private final AvailabilityService availabilityService;
    private final Validator fieldValidator;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FoodMenu> findAllFoodMenu() {
        log.info("Entering findAllFoodMenu()");
        List<FoodMenu> foodMenus = foodMenuRepository.findAll();
        log.info("Leaving findAllFoodMenu()");
        return foodMenus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FoodMenu findFoodMenuById(long foodMenuId) {
        log.info("Entering findFoodMenuById()");
        if (foodMenuId <= 0) {
            throw new CustomException("The Food Menu Id should be Greater than Zero.", HttpStatus.BAD_REQUEST);
        }

        log.info("Leaving findFoodMenuById()");
        return foodMenuRepository.findById(foodMenuId)
                .orElseThrow(() -> new CustomException("The Food Menu not Found", HttpStatus.NOT_FOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FoodMenu saveFoodMenu(FoodMenuDto foodMenuDto) {
        log.info("Entering saveFoodMenu()");
        Set<ConstraintViolation<FoodMenu>> constraintViolationSet = fieldValidator.validate(foodMenuDto.getFoodMenu());
        ValidationException.handlingException(constraintViolationSet);
        for (var foodItem : foodMenuDto.getFoodItems()) {
            Set<ConstraintViolation<FoodItem>> constraintViolationSet1 = fieldValidator.validate(foodItem);
            ValidationException.handlingException(constraintViolationSet1);
            foodItemService.findFoodItemById(foodItem.getId());
        }

        for (var availability : foodMenuDto.getAvailabilityList()) {
            Set<ConstraintViolation<Availability>> constraintViolationSet2 = fieldValidator.validate(availability);
            ValidationException.handlingException(constraintViolationSet2);
            availabilityService.findAvailabilityById(availability.getId());
        }

        if (!foodMenuRepository.existsByNameAndType(foodMenuDto.getFoodMenu().getName(),
                foodMenuDto.getFoodMenu().getType())) {
            foodMenuDto.getFoodMenu().setCreatedOn(Instant.now());
            foodMenuDto.getFoodMenu().setModifiedOn(Instant.now());
            FoodMenu addedFoodMenu = foodMenuRepository.save(foodMenuDto.getFoodMenu());
            for (var foodItem : foodMenuDto.getFoodItems()) {
                FoodMenuFoodItemMap foodMenuFoodItemMap = new FoodMenuFoodItemMap();
                foodMenuFoodItemMap.setFoodMenu(addedFoodMenu);
                foodMenuFoodItemMap.setFoodItem(foodItem);
                foodMenuFoodItemMap.setCreatedOn(Instant.now());
                foodMenuFoodItemMap.setModifiedOn(Instant.now());
                foodMenuFoodItemMapService.saveFoodMenuFoodItemMap(foodMenuFoodItemMap);
            }

            for (var availability : foodMenuDto.getAvailabilityList()) {
                FoodMenuAvailabilityMap foodMenuAvailabilityMap = new FoodMenuAvailabilityMap();
                foodMenuAvailabilityMap.setFoodMenu(addedFoodMenu);
                foodMenuAvailabilityMap.setAvailability(availability);
                foodMenuAvailabilityMap.setCreatedOn(Instant.now());
                foodMenuAvailabilityMap.setModifiedOn(Instant.now());
                foodMenuAvailabilityMapService.saveFoodMenuAvailabilityMap(foodMenuAvailabilityMap);
            }

            log.info("Leaving saveFoodMenu()");
            return addedFoodMenu;
        } else {
            throw new CustomException("The Food Menu is Already Exists", HttpStatus.ALREADY_REPORTED);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void updateFoodMenu(long foodMenuId, FoodMenuDto foodMenuDto) {
        log.info("Entering updateFoodMenu()");
        FoodMenu foodMenu = findFoodMenuById(foodMenuId);
        foodMenuDto.setFoodMenu(foodMenu);
        for (var foodItem : foodMenuDto.getFoodItems()) {
            Set<ConstraintViolation<FoodItem>> constraintViolationSet1 = fieldValidator.validate(foodItem);
            ValidationException.handlingException(constraintViolationSet1);
            foodItemService.findFoodItemById(foodItem.getId());
        }

        for (var availability : foodMenuDto.getAvailabilityList()) {
            Set<ConstraintViolation<Availability>> constraintViolationSet2 = fieldValidator.validate(availability);
            ValidationException.handlingException(constraintViolationSet2);
            availabilityService.findAvailabilityById(availability.getId());
        }

        List<FoodMenuFoodItemMap> listOfFoodMenuFoodItemMap =
                foodMenuFoodItemMapService.getFoodMenuFoodItemMapByFoodMenu(foodMenuDto.getFoodMenu());
        List<FoodMenuAvailabilityMap> listOfFoodMenuAvailabilityMap =
                foodMenuAvailabilityMapService.getFoodMenuAvailabilityMapByFoodMenu(foodMenuDto.getFoodMenu());
        Set<String> retrievedFoodItemName = listOfFoodMenuFoodItemMap.stream()
                .map(foodMenuFoodItemMap -> foodMenuFoodItemMap.getFoodItem().getName()).collect(Collectors.toSet());
        Set<String> foodItemNames =
                foodMenuDto.getFoodItems().stream().map(FoodItem::getName).collect(Collectors.toSet());
        boolean containsAll = retrievedFoodItemName.containsAll(foodItemNames);
        if (!containsAll) {
            listOfFoodMenuFoodItemMap.forEach(foodMenuFoodItemMap -> {
                if (!foodItemNames.contains(foodMenuFoodItemMap.getFoodItem().getName())) {
                    foodMenuFoodItemMapService.deleteFoodMenuFoodItemMapById(foodMenuFoodItemMap.getId());
                }

            });
            foodMenuDto.getFoodItems().forEach(foodItem -> {
                if (!retrievedFoodItemName.contains(foodItem.getName())) {
                    FoodMenuFoodItemMap newFoodMenuFoodItemMap = new FoodMenuFoodItemMap();
                    newFoodMenuFoodItemMap.setFoodMenu(foodMenu);
                    newFoodMenuFoodItemMap.setFoodItem(foodItem);
                    newFoodMenuFoodItemMap.setCreatedOn(Instant.now());
                    newFoodMenuFoodItemMap.setModifiedOn(Instant.now());
                    foodMenuFoodItemMapService.saveFoodMenuFoodItemMap(newFoodMenuFoodItemMap);
                }

            });
        }

        Set<Long> availabilities =
                foodMenuDto.getAvailabilityList().stream().map(Availability::getId).collect(Collectors.toSet());
        Set<Long> retrievedAvailability = listOfFoodMenuAvailabilityMap.stream()
                .map(foodMenuAvailabilityMap -> foodMenuAvailabilityMap.getAvailability().getId())
                .collect(Collectors.toSet());
        boolean areAvailabilityEquals = listOfFoodMenuAvailabilityMap.stream()
                .map(foodMenuAvailabilityMap -> foodMenuAvailabilityMap.getAvailability().getId())
                .allMatch(availabilities::contains);
        if (!areAvailabilityEquals) {
            listOfFoodMenuAvailabilityMap.forEach(availabilityMap -> {
                if (!availabilities.contains(availabilityMap.getAvailability().getId())) {
                    foodMenuAvailabilityMapService.deleteFoodMenuAvailabilityMapById(availabilityMap.getId());
                }

            });
            foodMenuDto.getAvailabilityList().forEach(availability -> {
                if (!retrievedAvailability.contains(availability.getId())) {
                    FoodMenuAvailabilityMap foodMenuAvailabilityMap = new FoodMenuAvailabilityMap();
                    foodMenuAvailabilityMap.setFoodMenu(foodMenu);
                    foodMenuAvailabilityMap.setAvailability(availability);
                    foodMenuAvailabilityMap.setCreatedOn(Instant.now());
                    foodMenuAvailabilityMap.setModifiedOn(Instant.now());
                    foodMenuAvailabilityMapService.saveFoodMenuAvailabilityMap(foodMenuAvailabilityMap);
                }

            });
        }

        foodMenuDto.getFoodMenu().setModifiedOn(Instant.now());
        foodMenuRepository.save(foodMenuDto.getFoodMenu());
        log.info("Leaving updateFoodMenu()");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFoodMenu(FoodMenu foodMenu) {
        log.info("Entering deleteFoodMenu()");
        foodMenuRepository.delete(foodMenu);
        log.info("Leaving deleteFoodMenu()");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFoodMenuById(long foodMenuId) {
        log.info("Entering deleteFoodMenuById()");
        FoodMenu foodMenu = findFoodMenuById(foodMenuId);
        List<FoodMenuFoodItemMap> listOfFoodMenuFoodItemMap =
                foodMenuFoodItemMapService.getFoodMenuFoodItemMapByFoodMenu(foodMenu);
        List<FoodMenuAvailabilityMap> listOfFoodMenuAvailabilityMap =
                foodMenuAvailabilityMapService.getFoodMenuAvailabilityMapByFoodMenu(foodMenu);
        for (var foodMenuFoodItemMap : listOfFoodMenuFoodItemMap) {
            foodMenuFoodItemMapService.deleteFoodMenuFoodItemMapById(foodMenuFoodItemMap.getId());
        }

        for (var foodMenuAvailabilityMap : listOfFoodMenuAvailabilityMap) {
            foodMenuAvailabilityMapService.deleteFoodMenuAvailabilityMapById(foodMenuAvailabilityMap.getId());
        }

        foodMenuRepository.deleteById(foodMenuId);
        log.info("Leaving deleteFoodMenuById()");
    }
}